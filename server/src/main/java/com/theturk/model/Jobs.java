package com.theturk.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.theturk.model.audit.DateAudit;

@SuppressWarnings("serial")
@Entity
public class Jobs extends DateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 40)
	private String jobName;

	@Size(max = 200)
	private String description;

	@NotBlank
	@Size(max = 200)
	private String className;
	
	@NotBlank
	@Size(max = 100)
	private String jarName;
	
	@NotBlank
	@Size(max = 400)
	private String jarPath;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getJarName() {
		return jarName;
	}

	public void setJarName(String jarName) {
		this.jarName = jarName;
	}

	public String getJarPath() {
		return jarPath;
	}

	public void setJarPath(String jarPath) {
		this.jarPath = jarPath;
	}

	@Override
	public String toString() {
		return "Jobs [id=" + id + ", jobName=" + jobName + ", description=" + description + ", className=" + className
				+ ", jarName=" + jarName + ", jarPath=" + jarPath + "]";
	}
	
	
}
