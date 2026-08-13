package com.dasther.ndramirez.simgap_daq.api.model.dto.devicedto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Información de un dispositivo registrado")
public class DeviceResponseDto {

    @Schema(description = "Identificador del dispositivo", example = "1")
    private Long idDevice;

    @Schema(description = "Nombre del dispositivo", example = "DAQ-RTG01")
    private String name;

    @Schema(description = "Dirección IPv4", example = "192.168.1.10")
    private String addressIp;

    @Schema(description = "Dirección MAC", example = "AA:BB:CC:DD:EE:01")
    private String mac;

    @Schema(description = "Número de rack del PLC", example = "0")
    private Integer rack;

    @Schema(description = "Número de slot del PLC", example = "1")
    private Integer slot;

    @Schema(description = "ID de la grúa asociada", example = "1")
    private Long craneId;

    @Schema(description = "Código de la grúa asociada", example = "RTG01")
    private String craneName;

    @Schema(description = "Estado operativo", example = "true")
    private Boolean isOperational;
}
