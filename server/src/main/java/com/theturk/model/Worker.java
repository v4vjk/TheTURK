package com.theturk.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.theturk.model.audit.DateAudit;

@SuppressWarnings("serial")
@Entity
@Table(name = "workers", uniqueConstraints = {
		@UniqueConstraint(columnNames = {
				"workerName"
		})
})
public class Worker extends DateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 40)
	private String workerName;

	@Size(max = 200)
	private String description;
	    
//    @ManyToMany
//    @JoinTable(
//            name="workers_jobs",
//            joinColumns=@JoinColumn(name="job_id", referencedColumnName="id"),
//            inverseJoinColumns=@JoinColumn(name="worker_id", referencedColumnName="id")
//    )
//    @JsonIgnore
//    List<Job> jobs;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

//	public List<Job> getJobs() {
//		return jobs;
//	}
//
//	public void setJobs(List<Job> jobs) {
//		this.jobs = jobs;
//	}

	@Override
	public String toString() {
		return "Worker [id=" + id + ", workerName=" + workerName + ", description=" + description + "]";
	}


}
