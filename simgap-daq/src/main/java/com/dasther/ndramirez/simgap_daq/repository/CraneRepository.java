package com.dasther.ndramirez.simgap_daq.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dasther.ndramirez.simgap_daq.model.entity.Crane;

public interface CraneRepository extends JpaRepository <Crane, Long> {

    Optional <Crane> findByName(String name);
    
}
