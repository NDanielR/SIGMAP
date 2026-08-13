package com.dasther.ndramirez.simgap_daq.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI sigmapOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("SIGMAP DAQ API")
                        .version("v1")
                        .description(
                                "API para administrar grúas, dispositivos IoT "
                                        + "y registros de horómetros."
                        ));
    }
}
