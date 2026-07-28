package com.dasther.ndramirez.simgap_daq.model.entity.crane;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

import com.dasther.ndramirez.simgap_daq.model.entity.device.Device;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Crane")

public class Crane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCrane")
    private Long idCrane;

    @NotBlank
    @Pattern(
            regexp = "^(RTG(0[1-9]|1[0-9])|QC0[1-6])$",
            message = "El código debe estar entre RTG01-RTG19 o QC01-QC06"
        )
    @Column(name = "Name", nullable = false, length = 5, unique = true)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "CraneType", nullable = false, length = 3)
    private CraneType type;

    @NotNull
    @Column(name = "IsOperational", nullable = false)
    private Boolean isOperational = true;

    @OneToMany(mappedBy = "crane")
    private List<Device> diveces = new ArrayList<>();
}
