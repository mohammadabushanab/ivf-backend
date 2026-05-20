package com.ivf.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivf.entitis.OPUEntity;

@Repository
public interface OPURepository extends JpaRepository<OPUEntity, Long> {

}