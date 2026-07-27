package com.dasther.ndramirez.simgap_daq.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dasther.ndramirez.simgap_daq.model.entity.Device;
import com.dasther.ndramirez.simgap_daq.repository.CraneRepository;
import com.dasther.ndramirez.simgap_daq.repository.DeviceRepository;

@Configuration
public class TestDataLoader {
    
    @Bean
    @ConditionalOnProperty(
        name ="app.test-data.ennabled",
        havingValue = "true"
    )

    CommandLineRunner loadDevices( DeviceRepository deviceRepository,
        CraneRepository craneRepository){
            
            return args -> {
            var crane = craneRepository.findByName("RTG01")
                    .orElseThrow(() ->
                            new IllegalStateException(
                                    "Primero debe existir la grúa RTG01"
                            )
                    );

            if (!deviceRepository.existsByMac("AA:BB:CC:DD:EE:01")) {
                var device = new Device();
                device.setName("ESP32-RTG01-01");
                device.setAddressIp("192.168.1.101");
                device.setMac("AA:BB:CC:DD:EE:01");
                device.setCrane(crane);
                device.setIsOperational(true);

                deviceRepository.save(device);
            }
        };
    }
}
