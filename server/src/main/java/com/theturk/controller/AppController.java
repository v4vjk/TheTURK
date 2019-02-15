package com.theturk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AppController {


	@Value("${com.theturk.maxJobsPerWorker}")
	private String maxJobsPerWorker;
	
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @GetMapping("/maxJobsPerWorker")
    public String getMaxJobsPerWorker() {
    	logger.debug("Returning maxJobsPerWorker " + maxJobsPerWorker);
    	return maxJobsPerWorker;
    }
        
}
