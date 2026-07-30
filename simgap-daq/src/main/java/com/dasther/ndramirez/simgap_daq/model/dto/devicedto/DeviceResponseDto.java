package com.dasther.ndramirez.simgap_daq.model.dto.devicedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeviceResponseDto {

    private Long idDevice;
    private String name;
    private String addressIp;
    private String mac;
    private Long craneId;
    private String craneName;
    private Boolean isOperational;
}
