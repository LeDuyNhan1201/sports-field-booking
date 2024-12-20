package org.jakartaee5g23.sportsfieldbooking.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

// this configuration is for OpenAPI 3.0
@Configuration
@Profile("!prod")
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi publicApi(@Value("${openapi.service.api-docs}") String apiDocs) {
        return GroupedOpenApi.builder()
            .group(apiDocs)
            .packagesToScan("org.jakartaee5g23.sportsfieldbooking.controllers")
            .build();
    }

    @Bean
    public OpenAPI openAPI(
        @Value("${openapi.service.title}") String title,
        @Value("${openapi.service.description}") String description,
        @Value("${openapi.service.version}") String version,
        @Value("${openapi.service.server-url}") String serverUrl,
        @Value("${openapi.service.server-description}") String serverDescription
    ) {
        return new OpenAPI()
                .servers(List.of(new Server().url(serverUrl).description(serverDescription)))
                .info(new Info()
                        .title(title)
                        .description(description)
                        .version(version)
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")
                        )
                        .termsOfService("http://swagger.io/terms/")
                        .contact(new Contact()
                                .email("tech.infinitynet.org@gmail.com")
                                .name("Infinity Net Organization")
                        )
                )
                .components(
                        new Components()
//                                .addHeaders("Accept-Language", new Header()
//                                        .description("Language header")
//                                        .schema(new StringSchema()._default("en"))
//                                )
                                .addSecuritySchemes(
                                        "bearerAuth",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
//                .components(
//                        new Components()
//                                .addParameters("acceptLanguage", new Parameter()
//                                        .in(ParameterIn.HEADER.toString())
//                                        .name("Accept-Language")
//                                        .description("Language header")
//                                        .schema(new StringSchema()._default("en"))
//                                )
//                )
                .security(List.of(
                        new SecurityRequirement()
                                .addList("bearerAuth")));
    }

}
