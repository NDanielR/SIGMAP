package com.dasther.ndramirez.simgap_daq.model.dto.hourmeterdto;

import java.time.Instant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HourMeterResponseDto {

    private Long hourMeterId;
    private Long deviceId;
    private Long craneOn;
    private Long hoistOn;
    private Long trolleyOn;
    private Long gantryOn;
    private Long overlapOn;
    private Long boomOn;
    private Instant dateReception;
      
}
