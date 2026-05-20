package com.ivf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ivf.entitis.FreezingEntity;

@Repository
public interface FreezingRepository extends JpaRepository<FreezingEntity, Long> {


}