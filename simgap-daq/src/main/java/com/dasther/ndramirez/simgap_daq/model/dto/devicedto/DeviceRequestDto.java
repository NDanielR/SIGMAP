package com.dasther.ndramirez.simgap_daq.model.dto.devicedto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeviceRequestDto {

    @NotBlank(message = "El nombre del dispositivo es obligatorio")
    @Size(
            min = 3,
            max = 64,
            message = "El nombre debe tener entre 3 y 64 caracteres"
    )
    private String name;

    @NotBlank(message = "La dirección IP es obligatoria")
    @Pattern(
            regexp = "^(?:(?:25[0-5]|2[0-4]\\d|1?\\d?\\d)\\.){3}"
                    + "(?:25[0-5]|2[0-4]\\d|1?\\d?\\d)$",
            message = "La dirección IP debe ser una IPv4 válida"
    )
    private String addressIp;

    @NotBlank(message = "La dirección MAC es obligatoria")
    @Pattern(
            regexp = "^(?:[0-9A-Fa-f]{2}:){5}[0-9A-Fa-f]{2}$",
            message = "La dirección MAC debe tener el formato AA:BB:CC:DD:EE:FF"
    )
    private String mac;

    @NotNull(message = "El ID de la grúa es obligatorio")
    @Positive(message = "El ID de la grúa debe ser mayor que cero")
    private Long craneId;

    @NotNull(message = "El estado operativo del dispositivo es obligatorio")
    private Boolean isOperational;
}
