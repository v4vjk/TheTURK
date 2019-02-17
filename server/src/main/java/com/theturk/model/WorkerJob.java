package com.theturk.model;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.theturk.model.audit.DateAudit;

@SuppressWarnings("serial")
@Entity
@Table(name = "workers_jobs", uniqueConstraints = {
})
public class WorkerJob extends DateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "job_id")
	private Long jobId;

	@NotNull
	@Column(name = "worker_id")
	private Long workerId;

	@Transient
    private Map<String, String> params;


	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getJobId() {
		return jobId;
	}



	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}



	public Long getWorkerId() {
		return workerId;
	}



	public void setWorkerId(Long workerId) {
		this.workerId = workerId;
	}


	public Map<String, String> getParams() {
		return params;
	}



	public void setParams(Map<String, String> params) {
		this.params = params;
	}



	@Override
	public String toString() {
		return "WorkerJob [id=" + id + ", jobId=" + jobId + ", workerId=" + workerId + ", params=" + params + "]";
	}
	
}
