package com.dasther.ndramirez.simgap_daq.model.dto.error;

import java.time.Instant;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta estándar cuando una petición no puede procesarse")
public record ApiErrorResponse(
        @Schema(example = "2026-07-29T15:30:00Z")
        Instant timestamp,

        @Schema(example = "400")
        int status,

        @Schema(example = "Bad Request")
        String error,

        @Schema(example = "La petición contiene datos inválidos")
        String message,

        @Schema(example = "/api/v1/gruas")
        String path,

        @Schema(
                description = "Errores asociados a campos específicos",
                nullable = true
        )
        Map<String, String> fieldErrors
) {
}
