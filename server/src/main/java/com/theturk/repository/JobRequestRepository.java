package com.theturk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.theturk.model.JobRequest;

public interface JobRequestRepository extends JpaRepository<JobRequest, Long> {

	List<JobRequest> findAllByOrderByIdDesc();

}
