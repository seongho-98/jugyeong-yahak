package com.ssafy.http.swagger.configs;

import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Server;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Value("${aws.server.url}")
    private String SERVER_URL;

    @Bean
    public Docket api() {
        Server serverLocal = new Server("local", "http://localhost:8080", "for local usages", Collections.emptyList(), Collections.emptyList());
        Server testServer = new Server("server", SERVER_URL, "for server usages", Collections.emptyList(), Collections.emptyList());
        return new Docket(DocumentationType.OAS_30)
                .servers(serverLocal, testServer)
            .consumes(getConsumeContentTypes())
            .produces(getProduceContentTypes())
            .securityContexts(
                Arrays.asList(securityContext()))
            .securitySchemes(Arrays.asList(apiKey()))
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global",
            "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "JWT", "header");
    }
}