package com.dasther.ndramirez.simgap_daq.api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dasther.ndramirez.simgap_daq.api.model.entity.crane.Crane;
import com.dasther.ndramirez.simgap_daq.api.model.entity.crane.CraneType;
import com.dasther.ndramirez.simgap_daq.api.model.entity.device.Device;
import com.dasther.ndramirez.simgap_daq.api.repository.crane.CraneRepository;
import com.dasther.ndramirez.simgap_daq.api.repository.device.DeviceRepository;

@Configuration
public class TestDataLoader {

    @Bean
    @ConditionalOnProperty(
            name = "app.test-data.enabled",
            havingValue = "true"
    )
    CommandLineRunner loadTestData(
            DeviceRepository deviceRepository,
            CraneRepository craneRepository) {

        return args -> {
            var rtg01 = getOrCreateCrane(
                    craneRepository, "RTG01", CraneType.RTG);
            var rtg02 = getOrCreateCrane(
                    craneRepository, "RTG02", CraneType.RTG);
            var qc01 = getOrCreateCrane(
                    craneRepository, "QC01", CraneType.QC);
            var qc02 = getOrCreateCrane(
                    craneRepository, "QC02", CraneType.QC);

            configureDevice(
                    deviceRepository,
                    "ESP32-RTG01-01",
                    "192.168.1.101",
                    0,
                    1,
                    "AA:BB:CC:DD:EE:01",
                    rtg01);

            configureDevice(
                    deviceRepository,
                    "ESP32-RTG02-01",
                    "192.168.1.102",
                    0,
                    1,
                    "AA:BB:CC:DD:EE:02",
                    rtg02);

            configureDevice(
                    deviceRepository,
                    "ESP32-QC01-01",
                    "192.168.1.103",
                    0,
                    1,
                    "AA:BB:CC:DD:EE:03",
                    qc01);

            configureDevice(
                    deviceRepository,
                    "ESP32-QC02-01",
                    "192.168.1.104",
                    0,
                    1,
                    "AA:BB:CC:DD:EE:04",
                    qc02);
        };
    }

    private Crane getOrCreateCrane(
            CraneRepository craneRepository,
            String name,
            CraneType type) {

        return craneRepository.findByName(name)
                .orElseGet(() -> {
                    var crane = new Crane();
                    crane.setName(name);
                    crane.setType(type);
                    crane.setIsOperational(true);
                    return craneRepository.save(crane);
                });
    }

    private void configureDevice(
            DeviceRepository deviceRepository,
            String name,
            String addressIp,
            Integer rack,
            Integer slot,
            String mac,
            Crane crane) {

        var device = deviceRepository.findByName(name)
                .orElseGet(Device::new);
        device.setName(name);
        device.setAddressIp(addressIp);
        device.setMac(mac);
        device.setRack(rack);
        device.setSlot(slot);
        device.setCrane(crane);
        device.setIsOperational(true);
        deviceRepository.save(device);
    }
}
