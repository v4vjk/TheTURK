package com.theturk.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.theturk.model.Job;
import com.theturk.repository.JobRepository;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

	@Value("${com.theturk.fileUploadLocation}")
	private String fileUploadLocation;
	  
    @Autowired
    private JobRepository jobRepository;
    

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    @GetMapping("/all")
    public List<Job> getAllWorkers() {
    	logger.debug("Returning all jobs");
    	return jobRepository.findAll();
    }
    
    @DeleteMapping(path ={"/{id}"})
    @Transactional
    public boolean dleteJob(@PathVariable("id") long id) {
    	logger.debug("deleting Job " + id);
    	
        Optional<Job> Job = jobRepository.findById(id);
        if(Job != null){
        	jobRepository.delete(Job.get());
        	return true;
        }
        return false;
        
    }
    
    @PostMapping("/updatejob")
    public boolean updateJob(@RequestBody Job job) {
    	logger.debug("updating job " + job);

    	Optional<Job> existing = jobRepository.findById(job.getId());
    	if(existing != null) {
    		
    		existing.get().setJobName(job.getJobName());
    		existing.get().setDescription(job.getDescription());
    		existing.get().setClassName(job.getClassName());
    		existing.get().setJarName(job.getJarName());
    		existing.get().setJarPath(job.getJarPath());
    		
    		jobRepository.save(existing.get());
    		
    		return true;
    	}

    	return false;
    }
    
    @PostMapping("/addjob")
    public boolean addJob(@RequestBody Job job) {
    	logger.debug("adding Job " + job);
    	
    	if(jobRepository.count() < 3) {
        	return jobRepository.save(job) != null;
    	}
    	return false;
    }
    
    @PostMapping("/upload")
    public ResponseEntity<Object>  handleFileUpload( 
            @RequestParam("file") MultipartFile file){
            String name = "test11";
        	logger.debug("uploading file " + file.getOriginalFilename());

        if (!file.isEmpty()) {
        	name = file.getOriginalFilename();
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = 
                        new BufferedOutputStream(new FileOutputStream(new File(fileUploadLocation + File.separator + name )));
                stream.write(bytes);
                stream.close();
//                return "You successfully uploaded " + name + " into " + name + "-uploaded !";
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
//                return "You failed to upload " + name + " => " + e.getMessage();
            	e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
//            return "You failed to upload " + name + " because the file was empty.";
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
