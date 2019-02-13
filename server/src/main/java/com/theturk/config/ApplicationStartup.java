package com.theturk.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

	@Value("${com.theturk.fileUploadLocation}")
	private String fileUploadLocation;

	/**
	 * This event is executed as late as conceivably possible to indicate that 
	 * the application is ready to service requests.
	 */
	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {

		//Create file upload directory if not exists
		File directory = new File(fileUploadLocation);

		if(!directory.exists()){
			System.out.println(directory.mkdirs());
		}
	}
}
