package com.dasther.ndramirez.simgap_daq.repository.crane;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dasther.ndramirez.simgap_daq.model.entity.crane.Crane;

public interface CraneRepository extends JpaRepository <Crane, Long> {

    Optional <Crane> findByName(String name);

    boolean existsByNameAndIdCraneNot(String name, Long idCrane);

    List<Crane> findByNameContainingIgnoreCaseOrderByNameAsc(String text);
    
}
