package com.dasther.ndramirez.simgap_daq.api.repository.hourmeter;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dasther.ndramirez.simgap_daq.api.model.entity.hourmeter.HourMeters;

public interface HourMeterRepository extends JpaRepository <HourMeters,Long> {
    
    List<HourMeters> findByDevice_Crane_NameIgnoreCaseOrderByDateReportDesc(String craneName);
}
