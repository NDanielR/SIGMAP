package com.dasther.ndramirez.simgap_daq.model.dto.cranedto;

import com.dasther.ndramirez.simgap_daq.model.entity.crane.CraneType;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CraneRequestDto {

    @NotBlank(message = "El nombre de la grúa es obligatorio")
    @Pattern(
            regexp = "^(RTG(0[1-9]|1[0-9])|QC0[1-6])$",
            message = "El nombre debe estar entre RTG01-RTG19 o QC01-QC06"
    )
    private String name;

    @NotNull(message = "El tipo de grúa es obligatorio")
    private CraneType type;

    @NotNull(message = "El estado operativo de la grúa es obligatorio")
    private Boolean isOperational;

    @AssertTrue(message = "El nombre de la grúa debe coincidir con su tipo")
    public boolean isNameCompatibleWithType() {
        if (name == null || type == null) {
            return true;
        }

        return switch (type) {
            case RTG -> name.startsWith("RTG");
            case QC -> name.startsWith("QC");
        };
    }
}
