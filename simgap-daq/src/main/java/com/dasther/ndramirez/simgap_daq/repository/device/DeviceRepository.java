package com.dasther.ndramirez.simgap_daq.repository.device;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dasther.ndramirez.simgap_daq.model.entity.device.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Boolean existsByMac(String mac);
}
