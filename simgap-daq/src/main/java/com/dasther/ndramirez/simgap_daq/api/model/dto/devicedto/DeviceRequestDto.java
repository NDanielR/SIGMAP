package com.dasther.ndramirez.simgap_daq.api.model.dto.devicedto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Datos requeridos para crear o actualizar un dispositivo")
public class DeviceRequestDto {

    @Schema(description = "Nombre único del dispositivo", example = "DAQ-RTG01")
    @NotBlank(message = "El nombre del dispositivo es obligatorio")
    @Size(
            min = 3,
            max = 64,
            message = "El nombre debe tener entre 3 y 64 caracteres"
    )
    private String name;

    @Schema(description = "Dirección IPv4 única", example = "192.168.1.10")
    @NotBlank(message = "La dirección IP es obligatoria")
    @Pattern(
            regexp = "^(?:(?:25[0-5]|2[0-4]\\d|1?\\d?\\d)\\.){3}"
                    + "(?:25[0-5]|2[0-4]\\d|1?\\d?\\d)$",
            message = "La dirección IP debe ser una IPv4 válida"
    )
    private String addressIp;

    @Schema(
            description = "Dirección MAC única",
            example = "AA:BB:CC:DD:EE:01",
            pattern = "^(?:[0-9A-Fa-f]{2}:){5}[0-9A-Fa-f]{2}$"
    )
    @NotBlank(message = "La dirección MAC es obligatoria")
    @Pattern(
            regexp = "^(?:[0-9A-Fa-f]{2}:){5}[0-9A-Fa-f]{2}$",
            message = "La dirección MAC debe tener el formato AA:BB:CC:DD:EE:FF"
    )
    private String mac;

    @Schema(description = "Número de rack del PLC (0 a 7)", example = "0")
    @NotNull(message = "El rack es obligatorio")
    @Min(value = 0, message = "El rack debe estar entre 0 y 7")
    @Max(value = 7, message = "El rack debe estar entre 0 y 7")
    private Integer rack;

    @Schema(description = "Número de slot del PLC (0 a 31)", example = "1")
    @NotNull(message = "El slot es obligatorio")
    @Min(value = 0, message = "El slot debe estar entre 0 y 31")
    @Max(value = 31, message = "El slot debe estar entre 0 y 31")
    private Integer slot;

    @Schema(description = "ID de la grúa asociada", example = "1")
    @NotNull(message = "El ID de la grúa es obligatorio")
    @Positive(message = "El ID de la grúa debe ser mayor que cero")
    private Long craneId;

    @Schema(
            description = "Indica si el dispositivo está operativo",
            example = "true"
    )
    @NotNull(message = "El estado operativo del dispositivo es obligatorio")
    private Boolean isOperational;
}
