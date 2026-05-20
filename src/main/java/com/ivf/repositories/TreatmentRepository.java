package com.ivf.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivf.entitis.TreatmentEntity;

@Repository
public interface TreatmentRepository extends JpaRepository<TreatmentEntity, Long> {

}