package com.ndanielr.daq_api.model.dto;

import com.ndanielr.daq_api.model.entity.Type;

public record CreateCraneDto(

    String name,
    
    String ip,

    Integer rack,

    Integer slot,

    Type type

) {}
