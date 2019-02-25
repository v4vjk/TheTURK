package com.theturk.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theturk.model.WorkerJob;
import com.theturk.repository.ExecutionRepository;
import com.theturk.services.ExecutionService;

@RestController
@EnableAsync
@RequestMapping("/api/execution")
public class ExecutionController {
	@Autowired
    private ExecutionRepository executionRepository;
    
	@Autowired
    private ExecutionService executionService;
    
    private static final Logger logger = LoggerFactory.getLogger(ExecutionController.class);

    @PostMapping("/all")
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
    
    @PostMapping("/doexecute")
    public void doExecuteWorkerJob(@RequestBody WorkerJob workerJob) {
    	logger.debug("executing job " + workerJob);

    	executionService.executeJob(workerJob);
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
