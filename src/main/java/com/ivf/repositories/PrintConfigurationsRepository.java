package com.ivf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivf.entitis.PrintConfigurationsEntity;


@Repository
public interface PrintConfigurationsRepository extends JpaRepository<PrintConfigurationsEntity, Long>{

}
