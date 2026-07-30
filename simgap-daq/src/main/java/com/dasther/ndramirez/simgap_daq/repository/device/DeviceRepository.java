package com.dasther.ndramirez.simgap_daq.repository.device;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dasther.ndramirez.simgap_daq.model.entity.device.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    Optional<Device> findByName(String name);

    List<Device> findByNameContainingIgnoreCaseOrderByNameAsc(String text);

    boolean existsByName(String name);

    boolean existsByAddressIp(String addressIp);

    boolean existsByMacIgnoreCase(String mac);

    boolean existsByNameAndIdDeviceNot(String name, Long idDevice);

    boolean existsByAddressIpAndIdDeviceNot(
            String addressIp,
            Long idDevice
    );

    boolean existsByMacIgnoreCaseAndIdDeviceNot(
            String mac,
            Long idDevice
    );
}
