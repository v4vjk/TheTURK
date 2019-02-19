package com.theturk.services;

import java.time.Instant;
import java.util.HashMap;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.theturk.model.JobRequest;
import com.theturk.model.WorkerJob;
import com.theturk.repository.ExecutionRepository;
import com.theturk.repository.JobRepository;
import com.theturk.repository.JobRequestRepository;

@Service
public class ExecutionService {

	@Value("${com.theturk.fileUploadLocation}")
	private String fileUploadLocation;

	@Value("${com.theturk.maxJobsPerWorker}")
	private Long maxJobsPerWorker;

	@Value("${com.theturk.maxTimeToExecuteJobInSec}")
	private Long maxTimeToExecuteJobInSec;

	@Autowired
	private ExecutionRepository executionRepository;
    
	@Autowired
    private JobRequestRepository jobRequestRepository;
    
	@Autowired
	private JobRepository jobRepository;
	
	private WeakHashMap<String, ThreadPoolExecutor> tpeByWorker = new WeakHashMap<String, ThreadPoolExecutor>(); 

	private static final Logger logger = LoggerFactory.getLogger(ExecutionService.class);
    
	private ThreadPoolExecutor createTPE(String workerId) {
		logger.debug("creating Semaphore for \""+workerId+'"');
        return new ThreadPoolExecutor(maxJobsPerWorker.intValue(), maxJobsPerWorker.intValue(), 1L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
    }
    
	@Async
	public void executeJob(WorkerJob workerJob) {
		logger.debug("executing job service " + workerJob);
		
//		semaphoresByWorker.computeIfAbsent(workerJob.getWorkerId(), null);
		ThreadPoolExecutor executor = tpeByWorker.computeIfAbsent(workerJob.getWorkerId().toString(), id -> createTPE(id));

//		ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 1L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
		JobRequest jobRequest = new JobRequest();

		JobTask jobTask = new JobTask(workerJob, fileUploadLocation, executionRepository, jobRequest, jobRepository);
		
		Future<JobRequest> future = null;
		
		try {
			jobRequest.setWorkerId(workerJob.getWorkerId());
			jobRequest.setJobId(workerJob.getJobId());
			jobRequest.setStartTime(Instant.now());
			jobRequest.setInput(workerJob.getParams());
			jobRequest.setStatus("SUCCESS");
			
			future = executor.submit(jobTask);
        
            future.get(this.maxTimeToExecuteJobInSec, TimeUnit.SECONDS);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            future.cancel(true);
            logger.error("Terminated!");
			jobRequest.setStatus("FAILURE");
			jobRequest.setOutput(new HashMap<String, String>() {{
				put("ERROR", e.toString());
		    }});
            e.printStackTrace();
        } catch (RejectedExecutionException e) {
            logger.error("Rejected!");
			jobRequest.setStatus("REJECTED");
			jobRequest.setOutput(new HashMap<String, String>() {{
				put("ERROR", e.toString());
		    }});
            e.printStackTrace();
        } finally {
			jobRequest.setEndTime(Instant.now());
			this.jobRequestRepository.save(jobRequest);
        }
	}

}
