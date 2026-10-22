package com.ndanielr.daq_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ndanielr.daq_api.model.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long>{
    
}
