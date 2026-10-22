package com.ndanielr.daq_api.config;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ndanielr.daq_api.model.entity.Crane;
import com.ndanielr.daq_api.model.entity.HourmeterQC;
import com.ndanielr.daq_api.model.entity.HourmeterRTG;
import com.ndanielr.daq_api.model.entity.Location;
import com.ndanielr.daq_api.model.entity.Type;
import com.ndanielr.daq_api.repository.CraneRepository;
import com.ndanielr.daq_api.repository.HourmeterQCRepository;
import com.ndanielr.daq_api.repository.HourmeterRTGRepository;
import com.ndanielr.daq_api.repository.LocationRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CraneRepository craneRepository;
    private final HourmeterQCRepository hourmeterQCRepository;
    private final HourmeterRTGRepository hourmeterRTGRepository;
    private final LocationRepository locationRepository;

    public DataInitializer(
            CraneRepository craneRepository,
            HourmeterQCRepository hourmeterQCRepository,
            HourmeterRTGRepository hourmeterRTGRepository,
            LocationRepository locationRepository) {

        this.craneRepository = craneRepository;
        this.hourmeterQCRepository = hourmeterQCRepository;
        this.hourmeterRTGRepository = hourmeterRTGRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {

        // Evita repetir la carga si ya existen grúas.
        if (craneRepository.count() > 0) {
            return;
        }

        // Todas las fechas de prueba quedarán en el pasado.
        Instant referenceDate = Instant.now()
                .minus(1, ChronoUnit.MINUTES);

        // Crear 3 grúas QC.
        for (int equipment = 1; equipment <= 3; equipment++) {

            Crane crane = saveCrane(
                    "QC%02d".formatted(equipment),
                    "192.168.10." + (10 + equipment),
                    0,
                    2,
                    Type.QC
            );

            saveQcReadings(crane, equipment, referenceDate);

            saveLocation(
                    crane,
                    3.8800 + equipment * 0.0001,
                    -77.0300 + equipment * 0.0001,
                    referenceDate
            );
        }

        // Crear 5 grúas RTG.
        for (int equipment = 1; equipment <= 5; equipment++) {

            Crane crane = saveCrane(
                    "RTG%02d".formatted(equipment),
                    "192.168.20." + (10 + equipment),
                    0,
                    2,
                    Type.RTG
            );

            saveRtgReadings(crane, equipment, referenceDate);

            saveLocation(
                    crane,
                    3.8820 + equipment * 0.0001,
                    -77.0320 + equipment * 0.0001,
                    referenceDate
            );
        }
    }

    private Crane saveCrane(
            String name,
            String ip,
            Integer rack,
            Integer slot,
            Type type) {

        Crane crane = new Crane();

        crane.setName(name);
        crane.setIp(ip);
        crane.setRack(rack);
        crane.setSlot(slot);
        crane.setType(type);

        return craneRepository.save(crane);
    }

    private void saveQcReadings(
            Crane crane,
            int equipment,
            Instant referenceDate) {

        long base = equipment * 100_000L;

        for (int reading = 0; reading < 10; reading++) {

            // Diez lecturas, separadas por una hora.
            Instant captureDate = referenceDate.minus(
                    9L - reading,
                    ChronoUnit.HOURS
            );

            HourmeterQC hourmeter = new HourmeterQC();

            hourmeter.setCrane(crane);

            // Tiempos acumulados de ejemplo en segundos.
            hourmeter.setCraneOn(base + reading * 3600L);
            hourmeter.setHoistOn(base / 2 + reading * 1200L);
            hourmeter.setTrolleyOn(base / 3 + reading * 900L);
            hourmeter.setGantryOn(base / 4 + reading * 300L);
            hourmeter.setOverlapOn(base / 5 + reading * 200L);
            hourmeter.setBoomOn(base / 10 + reading * 60L);

            hourmeter.setDateRegistration(captureDate);

            hourmeterQCRepository.save(hourmeter);
        }
    }

    private void saveRtgReadings(
            Crane crane,
            int equipment,
            Instant referenceDate) {

        long base = equipment * 80_000L;

        for (int reading = 0; reading < 10; reading++) {

            Instant captureDate = referenceDate.minus(
                    9L - reading,
                    ChronoUnit.HOURS
            );

            HourmeterRTG hourmeter = new HourmeterRTG();

            hourmeter.setCrane(crane);

            // Tiempos acumulados de ejemplo en segundos.
            hourmeter.setCraneOn(base + reading * 3600L);
            hourmeter.setHoistOn(base / 2 + reading * 1000L);
            hourmeter.setTrolleyOn(base / 3 + reading * 800L);
            hourmeter.setGantryOn(base / 4 + reading * 600L);
            hourmeter.setOverlapOn(base / 5 + reading * 200L);

            hourmeter.setDateRegistration(captureDate);

            hourmeterRTGRepository.save(hourmeter);
        }
    }

    private void saveLocation(
            Crane crane,
            double latitude,
            double longitude,
            Instant captureDate) {

        Location location = new Location();

        location.setCrane(crane);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setDate(captureDate);

        locationRepository.save(location);
    }
}