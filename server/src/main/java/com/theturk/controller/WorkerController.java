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

import com.theturk.model.Worker;
import com.theturk.repository.WorkerRepository;

@RestController
@RequestMapping("/api/workers")
public class WorkerController {

    @Autowired
    private WorkerRepository workerRepository;
    

    private static final Logger logger = LoggerFactory.getLogger(WorkerController.class);

    @GetMapping("/all")
    public List<Worker> getAllWorkers() {
    	logger.debug("Returning all workers");
    	return workerRepository.findAll();
    }
    
    @DeleteMapping(path ={"/{id}"})
    @Transactional
    public boolean dleteWorker(@PathVariable("id") long id) {
    	logger.debug("deleting worker " + id);
    	
        Optional<Worker> worker = workerRepository.findById(id);
        if(worker != null){
        	workerRepository.delete(worker.get());
        	return true;
        }
        return false;
        
    }
    
    @PostMapping("/updateworker")
    public boolean updateWorker(@RequestBody Worker worker) {
    	logger.debug("updating worker " + worker);

    	Optional<Worker> existing = workerRepository.findById(worker.getId());
    	if(existing != null) {
    		
    		existing.get().setWorkerName(worker.getWorkerName());
    		existing.get().setDescription(worker.getDescription());
    		
    		workerRepository.save(existing.get());
    		
    		return true;
    	}

    	return false;
    }
    
    @PostMapping("/addworker")
    public boolean addWorker(@RequestBody Worker worker) {
    	logger.debug("adding worker " + worker);
    	
    	if(workerRepository.count() < 3) {
        	return workerRepository.save(worker) != null;
        	
    	}
    	return false;
    }
}
