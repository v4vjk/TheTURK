package com.theturk.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.theturk.model.WorkerJob;

@Repository
public interface ExecutionRepository extends JpaRepository<WorkerJob, Long> {


}
