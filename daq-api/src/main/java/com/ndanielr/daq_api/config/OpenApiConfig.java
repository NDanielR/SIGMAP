package com.ndanielr.daq_api.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "DAQ API - Grúas",
        version = "1.0",
        description = "Registro, consulta, actualización y eliminación de grúas y sus parámetros de conexión al PLC."))
public class OpenApiConfig {
}
