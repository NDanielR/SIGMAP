package com.dasther.ndramirez.simgap_daq.model.entity.device;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import com.dasther.ndramirez.simgap_daq.model.entity.crane.Crane;
import com.dasther.ndramirez.simgap_daq.model.entity.hourmeter.HourMeters;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Device")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdDevice")
    private Long idDevice;


    @NotBlank
    @Size(min = 3, max = 64, message = "El nombre debe tener entre 3 y 64 caracteres")
    @Column(name = "Name", nullable = false, length = 64, unique = true)
    private String name;


    @NotBlank
    @Size(max = 15, message = "La dirección IP no puede superar los 15 caracteres")
    @Pattern(regexp = "^(?:(?:25[0-5]|2[0-4]\\d|1?\\d?\\d)\\.){3}"
                + "(?:25[0-5]|2[0-4]\\d|1?\\d?\\d)$",
                message = "La dirección IP debe ser una IPv4 válida"
        )
    @Column(name = "Address_ip", nullable = false, length = 15, unique = true)
    private String addressIp;

    @NotBlank
    @Size(min = 17, max = 17, message = "La dirección MAC debe tener 17 caracteres")
    @Pattern(regexp = "^(?:[0-9A-Fa-f]{2}[:-]){5}[0-9A-Fa-f]{2}$",
                message = "La dirección MAC debe tener el formato AA:BB:CC:DD:EE:FF"
        )
    @Column(name = "Mac", nullable = false, length = 17, unique = true)
    private String mac;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "IdCrane", nullable = false)
    private Crane crane;


    @NotNull
    @Column(name = "isOperational", nullable = false)
    private Boolean isOperational = true;


    @OneToMany(mappedBy = "device")
    private List<HourMeters> reports = new ArrayList<>();
}
