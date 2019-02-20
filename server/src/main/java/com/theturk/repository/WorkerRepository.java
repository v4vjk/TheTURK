package com.theturk.repository;


import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.theturk.model.Worker;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {

	Optional<Worker> findById(Long workerId);

//	@Query(value = "INSERT INTO workers(worker_name, description) VALUES (:workerName, :description) HAVING count(*) < 2", nativeQuery = true)
	@Query(value = "INSERT INTO workers(worker_name, description) select :workerName, :description where  exists ((\n" + 
			"select count(a.id) from workers a HAVING count(a.id) < :maxWorkersAllowedToRegister));", nativeQuery = true)
    @Modifying
    @Transactional
    Integer insertUser(@Param("workerName") String workerName, @Param("description") String description,
    		@Param("maxWorkersAllowedToRegister") Long maxWorkersAllowedToRegister);
	
//	Object checkAndSave(Worker worker, Long maxWorkersAllowedToRegister);


}
