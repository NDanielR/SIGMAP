package com.dasther.ndramirez.simgap_daq.model.dto.hourmeterdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class HourMeterRequestDto {

    @NotNull(message = "El ID del dispositivo es obligatorio")
    @Positive(message = "El ID del dispositivo debe ser mayor que cero")
    private Long deviceId;

    @NotNull(message = "El horómetro de la grúa es obligatorio")
    @PositiveOrZero(message = "El horómetro de la grúa no puede ser negativo")
    private Long craneOn;

    @NotNull(message = "El horómetro del hoist es obligatorio")
    @PositiveOrZero(message = "El horómetro del hoist no puede ser negativo")
    private Long hoistOn;

    @NotNull(message = "El horómetro del trolley es obligatorio")
    @PositiveOrZero(message = "El horómetro del trolley no puede ser negativo")
    private Long trolleyOn;

    @NotNull(message = "El horómetro del gantry es obligatorio")
    @PositiveOrZero(message = "El horómetro del gantry no puede ser negativo")
    private Long gantryOn;

    @NotNull(message = "El horómetro de overlap es obligatorio")
    @PositiveOrZero(message = "El horómetro de overlap no puede ser negativo")
    private Long overlapOn;

    @PositiveOrZero(message = "El horómetro del boom no puede ser negativo")
    private Long boomOn;

    @NotNull(message = "La fecha del reporte es obligatoria")
    @PastOrPresent(message = "La fecha del reporte no puede estar en el futuro")
    private Instant dateReport;

}
