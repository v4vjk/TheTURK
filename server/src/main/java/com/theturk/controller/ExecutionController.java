package com.theturk.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theturk.model.WorkerJob;
import com.theturk.repository.ExecutionRepository;

@RestController
@RequestMapping("/api/execution")
public class ExecutionController {
    @Autowired
    private ExecutionRepository executionRepository;
    

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    @GetMapping("/all")
    public List<WorkerJob> getAllWorkersJobs() {
    	logger.debug("Returning all worker jobs");
    	return executionRepository.findAll();
    }
    
    @DeleteMapping(path ={"/{id}"})
    @Transactional
    public boolean dleteWorkerJob(@PathVariable("id") long id) {
    	logger.debug("deleting Worker Job " + id);
    	
        Optional<WorkerJob> Job = executionRepository.findById(id);
        if(Job != null){
        	executionRepository.delete(Job.get());
        	return true;
        }
        return false;
        
    }
    
    @PostMapping("/updateexecution")
    public boolean updateWorkerJob(@RequestBody WorkerJob workerJob) {
    	logger.debug("updating job " + workerJob);

    	Optional<WorkerJob> existing = executionRepository.findById(workerJob.getId());
    	if(existing != null) {
    		
    		existing.get().setJobId(workerJob.getJobId());
    		existing.get().setWorkerId(workerJob.getWorkerId());
    		
    		executionRepository.save(existing.get());
    		
    		return true;
    	}

    	return false;
    }
    
    @PostMapping("/addworkerjob")
    public boolean addWorkerJob(@RequestBody WorkerJob workerJob) {
    	logger.debug("adding Worker Job " + workerJob);
    	
    	if(executionRepository.count() < 3) {
        	return executionRepository.save(workerJob) != null;
    	}
    	return false;
    }
    
}
