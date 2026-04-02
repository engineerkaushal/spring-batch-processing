package com.engineer.batchprocessing.repository;

import com.engineer.batchprocessing.entity.States;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<States, Integer> {
}
