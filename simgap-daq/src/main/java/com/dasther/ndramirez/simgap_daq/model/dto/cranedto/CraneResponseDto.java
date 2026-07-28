package com.dasther.ndramirez.simgap_daq.model.dto.cranedto;

import com.dasther.ndramirez.simgap_daq.model.entity.crane.CraneType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CraneResponseDto {

    private Long idCrane;
    private String name;
    private CraneType type;
    private Boolean isOperational;
}
