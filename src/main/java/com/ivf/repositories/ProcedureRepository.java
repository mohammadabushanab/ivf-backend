package com.ivf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivf.entitis.ProcedureEntity;

@Repository
public interface ProcedureRepository extends JpaRepository<ProcedureEntity, Long> {

}