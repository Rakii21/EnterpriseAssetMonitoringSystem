package com.example.EnterpriseAssetMonitoringSystem.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info().title("Enterprise Asset Monitoring System").description("By Team")).tags(Arrays.asList(
                        new Tag().name("Authentication").description("Register and Login"),
                        new Tag().name("User").description("User Details"),
                        new Tag().name("Asset").description("Creation and assignment of asset"),
                        new Tag().name("Sensor Data").description("Temperature and pressure reading of asset"),
                        new Tag().name("Maintenance").description("Schedule and complete maintenance for asset"),
                        new Tag().name("Uptime Log").description("Uptime and downtime logs of asset")

                        ));

    }
}