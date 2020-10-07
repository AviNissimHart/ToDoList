package com.qa.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.persistence.domain.TList;

@Repository
public interface TListRepository extends JpaRepository<TList, Long> {

	// J - Java
    // P - Persistence
    // A - Application Programming Interface

//    List<TList> findByCategory(String category);

}
