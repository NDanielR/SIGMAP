package com.dasther.ndramirez.simgap_daq.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "HourMeters")

public class HourMeters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdHourmeter")
    private Long idHourmeter;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "IdDevice", nullable = false)
    private Device device;

    @NotNull
    @PositiveOrZero
    @Column(name = "CraneOn", nullable = false)
    private Long timeCraneOn;

    @NotNull
    @PositiveOrZero
    @Column(name = "HoistOn", nullable = false)
    private Long timeHoistOn;

    @NotNull
    @PositiveOrZero
    @Column(name = "TrolleyOn", nullable = false)
    private Long timeTrolleyOn;

    @NotNull
    @PositiveOrZero
    @Column(name = "GantryOn", nullable = false)
    private Long timeGantryOn;

    @NotNull
    @PositiveOrZero
    @Column(name = "OverlapOn", nullable = false)
    private Long timeOverlapOn;

    @PositiveOrZero
    @Column(name = "BoomOn")
    private Long timeBoomOn;

    @NotNull
    @PastOrPresent
    @Column(name = "DateReport", nullable = false)
    private Instant dateReport;

    @NotNull
    @PastOrPresent
    @Column(name = "DateReception", nullable = false, updatable = false)
    private Instant dateReception;

    @AssertTrue(message = "boom On debe ser nulo para RTG y obligatorio para QC")
    public boolean isBoomOnValidoParaTipoGrua() {
        if (device == null || device.getCrane() == null
                || device.getCrane().getType() == null) {
            return true;
        }

        return device.getCrane().getType() == CraneType.QC
                ? timeBoomOn != null
                : timeBoomOn == null;
    }

    @AssertTrue(message = "El dispositivo debe estar operativo para registrar el horómetro")
    public boolean isDeviceOperationalValid() {
        return device == null || Boolean.TRUE.equals(device.getIsOperational());
    }

    @AssertTrue(message = "La grúa debe estar operativa para registrar el horómetro")
    public boolean isCraneOperationalValid() {
        return device == null
                || device.getCrane() == null
                || Boolean.TRUE.equals(device.getCrane().getIsOperational());
    }
}
