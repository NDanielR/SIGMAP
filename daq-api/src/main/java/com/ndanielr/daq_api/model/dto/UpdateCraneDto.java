package com.ndanielr.daq_api.model.dto;

public record UpdateCraneDto(

    Long id,
    
    String name,
    
    String ip,
    
    Integer rack,
    
    Integer slot
) {}
