package com.theturk.controller;

import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theturk.model.Worker;
import com.theturk.repository.WorkerRepository;

@RestController
@RequestMapping("/api/workers")
public class WorkerController {

	@Autowired
	private WorkerRepository workerRepository;

	@Value("${com.theturk.maxWorkersAllowedToRegister}")
	private Long maxWorkersAllowedToRegister;    

	private static final Logger logger = LoggerFactory.getLogger(WorkerController.class);

	@PostMapping("/all")
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
	public @ResponseBody ResponseEntity<String> addWorker(@RequestBody Worker worker) {
		logger.debug("adding worker " + worker);

		int created = 0;
	    ObjectMapper mapper = new ObjectMapper();


		try {
			created = workerRepository.insertWorker(worker.getWorkerName(), worker.getDescription(), maxWorkersAllowedToRegister);
			logger.debug("worker created : " + created);

			if(created < 1) {
				return new ResponseEntity<String>(
						"Maximum limit to register Worker exceeded", 
					      HttpStatus.FORBIDDEN);
				}

//			return ResponseEntity.ok();
//			return new ResponseEntity<>(
//				      "Worker created", 
//				      HttpStatus.OK);
			
			return new ResponseEntity<String>(
					"{\"Worker created\":1}", 
				      HttpStatus.OK);
		} catch (Exception e) {
			try {
				return new ResponseEntity<String>(
						mapper.writeValueAsString("Unique constraint violeted " + e.getMessage()), 
					      HttpStatus.FORBIDDEN);
			} catch (JsonProcessingException e1) {
				return new ResponseEntity<String>(
						"{\"Unique constraint violeted\":1}", 
					      HttpStatus.FORBIDDEN);
			}
		}
	}
}
