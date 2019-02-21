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

	//insert query which checks if max number of workers to register is reached and inserts if not 
	@Query(value = "INSERT INTO workers(worker_name, description, created_at, updated_at) select :workerName, :description, current_timestamp, current_timestamp"
			+ " where  exists ((select count(a.id) from workers a HAVING count(a.id) < :maxWorkersAllowedToRegister));", nativeQuery = true)
    @Modifying
    @Transactional
    Integer insertWorker(@Param("workerName") String workerName, @Param("description") String description,
    		@Param("maxWorkersAllowedToRegister") Long maxWorkersAllowedToRegister);

}
