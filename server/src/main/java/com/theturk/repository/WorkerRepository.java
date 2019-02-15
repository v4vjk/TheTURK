package com.theturk.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.theturk.model.Worker;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {

	Optional<Worker> findById(Long workerId);


}
