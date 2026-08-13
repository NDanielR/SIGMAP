package com.dasther.ndramirez.simgap_daq.exception;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta estándar para todos los errores de la API")
public record ApiErrorResponse(
        @Schema(
                description = "Instante UTC en el que ocurrió el error",
                example = "2026-07-29T20:30:00Z"
        )
        Instant timestamp,

        @Schema(description = "Código de estado HTTP", example = "400")
        int status,

        @Schema(description = "Nombre del estado HTTP", example = "Bad Request")
        String error,

        @Schema(
                description = "Descripción legible del error",
                example = "El nombre de la grúa es obligatorio"
        )
        String message,

        @Schema(
                description = "Ruta HTTP que produjo el error",
                example = "/api/v1/gruas"
        )
        String path
) {
}
