package dev.marcus.curriculum.config.docs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Configuration
@OpenAPIDefinition
public class SpringDocConfig {
    @Value("${server.port}")
    private String serverPort;

    @Bean
    OpenAPI customOpenAPI() throws IOException {
    var key = "default";

    ApiResponse badRequest = new ApiResponse().content(
        new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
            new io.swagger.v3.oas.models.media.MediaType().addExamples(key,
                new Example().value(read("badRequestResponse"))))
    ).description("BAD REQUEST");

    ApiResponse notFound = new ApiResponse().content(
        new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
            new io.swagger.v3.oas.models.media.MediaType().addExamples(key,
                new Example().value(read("notFoundResponse"))))
    ).description("NOT FOUND");

    ApiResponse unauthorized = new ApiResponse().content(
        new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
            new io.swagger.v3.oas.models.media.MediaType().addExamples(key,
                new Example().value(read("unauthorizedResponse"))))
    ).description("UNAUTHORIZED");

    ApiResponse forbidden = new ApiResponse().content(
        new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
            new io.swagger.v3.oas.models.media.MediaType().addExamples(key,
                new Example().value(read("forbiddenResponse"))))
    ).description("FORBIDDEN");

    ApiResponse internalServerError = new ApiResponse().content(
        new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
            new io.swagger.v3.oas.models.media.MediaType().addExamples(key,
                new Example().value(read("internalServerErrorResponse"))))
    ).description("INTERNAL SERVER ERROR");

    var components = new Components();

    components.addResponses("badRequest", badRequest);
    components.addResponses("notFound", notFound);
    components.addResponses("unauthorized", unauthorized);
    components.addResponses("forbidden", forbidden);
    components.addResponses("internalServerError", internalServerError);

    components.addSecuritySchemes("basicScheme", new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("basic")
    ).addSecuritySchemes("bearerKey", new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
    );

    var servers = new ArrayList<Server>();
    servers.add(new Server().url("http://localhost:"+ serverPort +"/api").description("Localhost"));

    return new OpenAPI()
        .info(new Info()
            .title("Curriculum")
            .description("Curriculum - Api")
            .version("v1")
        )
        .components(components)
        .servers(servers);
    }

    @Bean
    GroupedOpenApi producersGroup() {
        String[] paths = {"/**"};
        return GroupedOpenApi.builder().group("Geral").pathsToMatch(paths).build();
    }

    @Value("classpath:springdoc-responses/responses.json")
    private Resource jsonFile;

    private String read(String key) throws IOException {
        try (var inputStream = jsonFile.getInputStream()) {
            String content = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            return new JSONObject(content).get(key).toString();
        }
    }
}
