package com.theturk.services;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.theturk.ITurkJob;
import com.theturk.model.Job;
import com.theturk.model.Worker;
import com.theturk.model.WorkerJob;
import com.theturk.repository.ExecutionRepository;
import com.theturk.repository.JobRepository;
import com.theturk.repository.WorkerRepository;
import com.theturk.util.DynamicURLClassLoader;

@Service
public class ExecutionService {

	@Value("${com.theturk.fileUploadLocation}")
	private String fileUploadLocation;
	
	@Autowired
	private ExecutionRepository executionRepository;
	
	@Autowired
	private WorkerRepository workerRepository;
	
	@Autowired
	private JobRepository jobRepository;


	private static final Logger logger = LoggerFactory.getLogger(ExecutionService.class);

	public boolean executeJob(WorkerJob workerJob) {
		logger.debug("executing job service " + workerJob);
		

		Optional<WorkerJob> existing = executionRepository.findByWorkerIdAndJobId(workerJob.getWorkerId(), workerJob.getJobId());
		
		if(existing.isPresent()) {
			existing.get().setUpdatedAt(Instant.now());
			executionRepository.save(existing.get());
		} else {
			executionRepository.save(workerJob);
		}
		
		Optional<Worker> currentWorker = workerRepository.findById(workerJob.getWorkerId());
		Optional<Job> currentJob = jobRepository.findById(workerJob.getJobId());
		
		try {
			 File f = new File(fileUploadLocation + File.separator + currentJob.get().getJarName());
			 executeJob(f.toURI().toURL(), currentJob.get().getClassName(), workerJob.getParams());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;		
	}
	
    public static void executeJob(URL url, String className, Map<String, String> params) throws IOException {
    	 URL[] classUrls = { url };
    	URLClassLoader sysLoader = new URLClassLoader(classUrls);

    	Method sysMethod;
		try {

	    	
			Class<ITurkJob> classToLoad = (Class<ITurkJob>) Class.forName(className, true, sysLoader);
//	    	Method method = classToLoad.getDeclaredMethod("perform");
//	    	Method method = classToLoad.getMethod("perform", Map.class);
	    	ITurkJob instance = classToLoad.newInstance();
	    	Map<String, String> hashMap = new HashMap<String, String>();
	    	hashMap.put("FIRST_NAME", "vijay");
	    	hashMap.put("LAST_NAME", "kumar");
//	    	Object result = method.invoke(instance, hashMap);
	    	Map<String, String> perform = instance.perform(params);
	    	System.out.println("perform result - " + perform);

		}  catch (Throwable t) {
            t.printStackTrace();
        } 
    	
//    	URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
//    	DynamicURLClassLoader dynaLoader = new DynamicURLClassLoader(urlClassLoader);
//    	
//    	dynaLoader.addURL(url);
//    	
//        try {
//               
//            Class classToLoad = Class.forName("antlr.ActionElement", true, dynaLoader);
//            Method method = classToLoad.getDeclaredMethod("myMethod");
//            Object instance = classToLoad.newInstance();
//            Object result = method.invoke(instance, url);
//            
//        } catch (Throwable t) {
//            t.printStackTrace();
//            throw new IOException("Error, could not add URL to system classloader");
//        }        
    }

}
