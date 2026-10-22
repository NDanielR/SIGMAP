package com.ndanielr.daq_api.model.dto;

import java.time.Instant;

public record HourmeterResponseDto(
    String craneName,
    Long craneOn,
    Long hoistOn,
    Long trolleyOn,
    Long gantryOn,
    Long overlapOn,
    Long boomOn,
    Instant dateRegistration
) {}
