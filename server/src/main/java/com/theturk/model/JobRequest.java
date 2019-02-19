package com.theturk.model;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "requests", uniqueConstraints = {
})
public class JobRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "job_id")
	private Long jobId;

	@NotNull
	@Column(name = "worker_id")
	private Long workerId;

    private Instant startTime;

    private Instant endTime;
    
    private String status;
    
    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="in_parameters", joinColumns=@JoinColumn(name="parameters_id"))
    private Map<String, String> input = new HashMap<String, String>();
    
    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="out_parameters", joinColumns=@JoinColumn(name="parameters_id"))
    private Map<String, String> output = new HashMap<String, String>();

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

	public Instant getStartTime() {
		return startTime;
	}

	public void setStartTime(Instant startTime) {
		this.startTime = startTime;
	}

	public Instant getEndTime() {
		return endTime;
	}

	public void setEndTime(Instant endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String, String> getInput() {
		return input;
	}

	public void setInput(Map<String, String> input) {
		this.input = input;
	}


	public Map<String, String> getOutput() {
		return output;
	}

	public void setOutput(Map<String, String> output) {
		this.output = output;
	}

	@Override
	public String toString() {
		return "JobRequest [id=" + id + ", jobId=" + jobId + ", workerId=" + workerId + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", status=" + status + ", input=" + input + ", output=" + output + "]";
	}

}
