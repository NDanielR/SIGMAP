package com.dasther.ndramirez.simgap_daq.api.model.dto.hourmeterdto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Lecturas de horómetro enviadas por un dispositivo IoT")
public class HourMeterRequestDto {

    @Schema(description = "ID del dispositivo que envía el reporte", example = "1")
    @NotNull(message = "El ID del dispositivo es obligatorio")
    @Positive(message = "El ID del dispositivo debe ser mayor que cero")
    private Long deviceId;

    @Schema(description = "Tiempo acumulado de la grúa", example = "200")
    @NotNull(message = "El horómetro de la grúa es obligatorio")
    @PositiveOrZero(message = "El horómetro de la grúa no puede ser negativo")
    private Long craneOn;

    @Schema(description = "Tiempo acumulado del hoist", example = "20")
    @NotNull(message = "El horómetro del hoist es obligatorio")
    @PositiveOrZero(message = "El horómetro del hoist no puede ser negativo")
    private Long hoistOn;

    @Schema(description = "Tiempo acumulado del trolley", example = "5")
    @NotNull(message = "El horómetro del trolley es obligatorio")
    @PositiveOrZero(message = "El horómetro del trolley no puede ser negativo")
    private Long trolleyOn;

    @Schema(description = "Tiempo acumulado del gantry", example = "3")
    @NotNull(message = "El horómetro del gantry es obligatorio")
    @PositiveOrZero(message = "El horómetro del gantry no puede ser negativo")
    private Long gantryOn;

    @Schema(description = "Tiempo acumulado de overlap", example = "2")
    @NotNull(message = "El horómetro de overlap es obligatorio")
    @PositiveOrZero(message = "El horómetro de overlap no puede ser negativo")
    private Long overlapOn;

    @Schema(
            description = "Tiempo acumulado del boom; obligatorio para QC "
                    + "y nulo para RTG",
            example = "20",
            nullable = true
    )
    @PositiveOrZero(message = "El horómetro del boom no puede ser negativo")
    private Long boomOn;

    @JsonFormat(shape = Shape.STRING, pattern = "dd/mm/yyyy hh:mm:ss" )
    @Schema(
            description = "Instante UTC en que el dispositivo generó el reporte",
            example = "2026-07-28T19:39:37Z",
            format = "date-time"
    )
    @NotNull(message = "La fecha del reporte es obligatoria")
    @PastOrPresent(message = "La fecha del reporte no puede estar en el futuro")
    private Instant dateReport;

}
