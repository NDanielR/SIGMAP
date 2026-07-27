package com.dasther.ndramirez.simgap_daq.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dasther.ndramirez.simgap_daq.model.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}
