package com.ndanielr.daq_api.model.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class HourmeterRTG {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "crane_id")
    private Crane crane;

    @NotNull(message = "El tiempo de encendido de la grúa es obligatorio")
    @PositiveOrZero(message = "El tiempo de la grúa no puede ser negativo")
    @Column(nullable = false)
    private Long craneOn;

    @NotNull(message = "El tiempo de hoist de la grúa es obligatorio")
    @PositiveOrZero(message = "El tiempo de la grúa no puede ser negativo")
    @Column(nullable = false)
    private Long hoistOn;

    @NotNull(message = "El tiempo de trolley de la grúa es obligatorio")
    @PositiveOrZero(message = "El tiempo de la grúa no puede ser negativo")
    @Column(nullable = false)
    private Long trolleyOn;

    @NotNull(message = "El tiempo de gantry de la grúa es obligatorio")
    @PositiveOrZero(message = "El tiempo de la grúa no puede ser negativo")
    @Column(nullable = false)
    private Long gantryOn;

    @NotNull(message = "El tiempo de overlap de la grúa es obligatorio")
    @PositiveOrZero(message = "El tiempo de la grúa no puede ser negativo")
    @Column(nullable = false)
    private Long overlapOn;

    @NotNull(message = "La fecha es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    @Column(nullable = false)
    private Instant dateRegistration;
}



  