package com.engineer.batchprocessing.repository;

import com.engineer.batchprocessing.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Page<User> findByUserName(String name, Pageable pageable);

}
