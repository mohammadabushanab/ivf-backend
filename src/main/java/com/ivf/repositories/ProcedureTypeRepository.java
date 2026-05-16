package com.ivf.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivf.entitis.ProcedureTypeEntity;

@Repository
public interface ProcedureTypeRepository extends JpaRepository<ProcedureTypeEntity, Long> {

}