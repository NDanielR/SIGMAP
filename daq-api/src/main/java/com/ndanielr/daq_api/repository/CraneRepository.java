package com.ndanielr.daq_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ndanielr.daq_api.model.entity.Crane;



public interface CraneRepository extends JpaRepository<Crane,Long>{
    Optional<Crane> findByNameIgnoreCaseContains(String name);

    boolean existsByName(String name);

    boolean existsByIp(String ip);

    boolean existsByNameAndIdNot(String name, Long id);
}
