package com.project.shopapp.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "E-commerce api ",
                version = "1.0.0",
                description = "Awesome api spring boot project"
        ),
        servers = {
                @Server(url = "http://localhost:8088", description = "Local Development Server"),
                @Server(url = "http://45.117.179.16:8088", description = "Production Server"),
        }
)

@SecurityScheme(
        name = "bearer-key", // Can be any name, used to reference this scheme in the @SecurityRequirement annotation
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Configuration
public class OpenApiConfig {

}

