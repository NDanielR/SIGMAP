package com.ndanielr.daq_api.model.dto;

import java.time.Instant;

public record LocationResponseDto(
    Long id,
    String craneName,
    Double latitude,
    Double longitude,
    Instant date
) {}
