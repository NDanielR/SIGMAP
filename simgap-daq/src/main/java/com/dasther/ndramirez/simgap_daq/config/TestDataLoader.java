package com.dasther.ndramirez.simgap_daq.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dasther.ndramirez.simgap_daq.model.entity.crane.Crane;
import com.dasther.ndramirez.simgap_daq.model.entity.crane.CraneType;
import com.dasther.ndramirez.simgap_daq.model.entity.device.Device;
import com.dasther.ndramirez.simgap_daq.repository.crane.CraneRepository;
import com.dasther.ndramirez.simgap_daq.repository.device.DeviceRepository;

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

            createDeviceIfMissing(
                    deviceRepository,
                    "ESP32-RTG01-01",
                    "192.168.1.101",
                    "AA:BB:CC:DD:EE:01",
                    rtg01);

            createDeviceIfMissing(
                    deviceRepository,
                    "ESP32-RTG02-01",
                    "192.168.1.102",
                    "AA:BB:CC:DD:EE:02",
                    rtg02);

            createDeviceIfMissing(
                    deviceRepository,
                    "ESP32-QC01-01",
                    "192.168.1.103",
                    "AA:BB:CC:DD:EE:03",
                    qc01);

            createDeviceIfMissing(
                    deviceRepository,
                    "ESP32-QC02-01",
                    "192.168.1.104",
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

    private void createDeviceIfMissing(
            DeviceRepository deviceRepository,
            String name,
            String addressIp,
            String mac,
            Crane crane) {

        if (deviceRepository.existsByMacIgnoreCase(mac)) {
            return;
        }

        var device = new Device();
        device.setName(name);
        device.setAddressIp(addressIp);
        device.setMac(mac);
        device.setCrane(crane);
        device.setIsOperational(true);
        deviceRepository.save(device);
    }
}
