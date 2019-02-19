package com.theturk.services;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.theturk.ITurkJob;
import com.theturk.model.Job;
import com.theturk.model.JobRequest;
import com.theturk.model.WorkerJob;
import com.theturk.repository.ExecutionRepository;
import com.theturk.repository.JobRepository;
import com.theturk.repository.JobRequestRepository;

public class JobTask implements Callable<JobRequest> {
	private static final Logger logger = LoggerFactory.getLogger(JobTask.class);

	private WorkerJob workerJob;

	private String fileUploadLocation;

	private ExecutionRepository executionRepository;
    
    private JobRequest jobRequest;
    
	private JobRepository jobRepository;
	
	public JobTask(WorkerJob workerJob, String fileUploadLocation, ExecutionRepository executionRepository,
			JobRequest jobRequest, JobRepository jobRepository) {
		this.workerJob = workerJob;
		this.executionRepository = executionRepository;
		this.jobRequest= jobRequest;
		this.jobRepository = jobRepository;
		this.fileUploadLocation = fileUploadLocation;
	}



	@Override
	public JobRequest call() throws Exception {
		logger.debug("executing job task " + workerJob);
		
		Optional<WorkerJob> existing = this.executionRepository.findByWorkerIdAndJobId(this.workerJob.getWorkerId(), this.workerJob.getJobId());

		if(existing.isPresent()) {
			//update updatedAt value if already exists
			existing.get().setUpdatedAt(Instant.now());
			this.executionRepository.save(existing.get());
		} else {
			//create new otherwise
			this.executionRepository.save(this.workerJob);
		}

		Optional<Job> currentJob = this.jobRepository.findById(this.workerJob.getJobId());

		Map<String, String> result = null;
		
		try {
			File jarFile = new File(this.fileUploadLocation + File.separator + currentJob.get().getJarName());
			
			URL[] classUrls = { jarFile.toURI().toURL() };
			URLClassLoader customLoader = new URLClassLoader(classUrls);
		
			jobRequest.setWorkerId(this.workerJob.getWorkerId());
			jobRequest.setJobId(this.workerJob.getJobId());
			jobRequest.setStartTime(Instant.now());
			jobRequest.setInput(this.workerJob.getParams());
			jobRequest.setStatus("SUCCESS");
			
			@SuppressWarnings("unchecked")
			Class<ITurkJob> classToLoad = (Class<ITurkJob>) Class.forName(currentJob.get().getClassName(), true, customLoader);
			ITurkJob instance = classToLoad.newInstance();

			result = instance.perform(this.workerJob.getParams());
			logger.debug("perform job result - " + result);
			
			jobRequest.setOutput(result);

		}  catch (Throwable t) {
			jobRequest.setStatus("FAILURE");
			result = new HashMap<String, String>();
			result.put("ERROR", t.toString());
			jobRequest.setOutput(result);
		}
		
		return jobRequest;
	}

}
