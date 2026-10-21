package com.ndanielr.daq_api.model.dto;

import java.util.Date;

public record HourmeterResponseDto(
    String craneName,
    Long craneOn,
    Long hoistOn,
    Long trolleyOn,
    Long gantryOn,
    Long overlapOn,
    Long boomOn,
    Date dateRegistration
) {}
