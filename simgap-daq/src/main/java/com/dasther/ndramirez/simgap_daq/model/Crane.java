package com.dasther.ndramirez.simgap_daq.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "Crane",
        uniqueConstraints = @UniqueConstraint(name = "uk_Crane_ID", columnNames = "IdCrane")
)
public class Crane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Pattern(
            regexp = "^(RTG(0[1-9]|1[0-9])|QC0[1-6])$",
            message = "El código debe estar entre RTG01-RTG19 o QC01-QC06"
    )
    @Column(nullable = false, length = 5)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "CraneType", nullable = false, length = 3)
    private CraneType tipo;

    @NotNull
    @Column(nullable = false)
    private Boolean isOperational = true;
}
