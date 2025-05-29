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
                        new Tag().name("Auth").description("Register, Login and Logout users"),
                        new Tag().name("User").description("Assign Role"),
                        new Tag().name("Asset").description("Various Product"),
                        new Tag().name("Sensor Data").description("Temperature and Pressure Reading"),
                        new Tag().name("Maintenance").description("Schedule Maintenance"),
                        new Tag().name("Uptime Log").description("Updated Log")

                        ));

    }
}