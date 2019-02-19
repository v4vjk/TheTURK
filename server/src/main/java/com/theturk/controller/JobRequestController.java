package com.theturk.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theturk.model.JobRequest;
import com.theturk.repository.JobRequestRepository;

@RestController
@RequestMapping("/api/request")
public class JobRequestController {

	  
    @Autowired
    private JobRequestRepository jobRequestRepository;
    

    private static final Logger logger = LoggerFactory.getLogger(JobRequestController.class);

    @PostMapping("/all")
    public List<JobRequest> getAllJobRequests() {
    	logger.debug("Returning all job requests");
    	return jobRequestRepository.findAllByOrderByIdDesc();
    }
}
