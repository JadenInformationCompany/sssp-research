package com.szhis.frsoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.szhis.frsoft.entity.JavaPojo;

public interface TransactionTestDao extends JpaRepository<JavaPojo, Integer> {
}
