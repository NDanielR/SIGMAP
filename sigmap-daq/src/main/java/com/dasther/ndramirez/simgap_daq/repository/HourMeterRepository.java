package com.dasther.ndramirez.simgap_daq.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dasther.ndramirez.simgap_daq.model.entity.HourMeters;

public interface HourMeterRepository extends JpaRepository <HourMeters,Long> {
    
}
