package com.qa.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.persistence.domain.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	// J - Java
    // P - Persistence
    // A - Application Programming Interface

//    List<Task> findByName(String name);
//
//    List<Task> findByType(String type);
}
