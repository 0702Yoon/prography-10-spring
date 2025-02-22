package com.example.tableTennis.global.config;

import com.example.tableTennis.global.response.example.ExampleHolder;
import com.example.tableTennis.global.response.ResponseType;
import com.example.tableTennis.global.response.example.error.ApiErrorCodeExample;
import com.example.tableTennis.global.response.example.error.ApiErrorCodeExamples;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
            .title("프로그라피 탁구 API DOCs")
            .description("Swagger UI")
            .version("v1");
    }

    @Bean
    public OperationCustomizer customizer() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            ApiErrorCodeExamples apiResponseCodeExamples = handlerMethod.getMethodAnnotation(
                ApiErrorCodeExamples.class);

            if (apiResponseCodeExamples != null) {
                generateResponseCodeExample(operation, apiResponseCodeExamples.value());
            } else {
                ApiErrorCodeExample apiSuccessCodeExample = handlerMethod.getMethodAnnotation(
                    ApiErrorCodeExample.class);
                if (apiSuccessCodeExample != null) {
                    generateResponseCodeExample(operation, apiSuccessCodeExample.value());
                }
            }
            return operation;
        };
    }

    private void generateResponseCodeExample(Operation operation,
        ResponseType[] responseTypes) {
        ApiResponses responses = operation.getResponses();

        // ExampleHolder(에러 응답값) 객체를 만들고 에러 코드별로 그룹화
        Map<Integer, List<ExampleHolder>> statusWithExampleHolders = Arrays.stream(responseTypes)
            .map(
                responseType -> ExampleHolder.builder()
                    .holder(getSwaggerExample(responseType))
                    .code(responseType.getHttpStatusCode())
                    .name(responseType.name())
                    .build()
            )
            .collect(Collectors.groupingBy(ExampleHolder::getCode));
        addExamplesToResponses(responses, statusWithExampleHolders);
    }

    private void generateResponseCodeExample(Operation operation,
        ResponseType responseType) {
        ApiResponses responses = operation.getResponses();
        // ExampleHolder 객체 생성 및 ApiResponses에 추가

        ExampleHolder exampleHolder = ExampleHolder.builder()
            .holder(getSwaggerExample(responseType))
            .name(responseType.name())
            .code(responseType.getHttpStatusCode())
            .build();

        addExamplesToResponses(responses, exampleHolder);

    }

    private void addExamplesToResponses(ApiResponses responses, ExampleHolder exampleHolder) {
        Content content = new Content();
        MediaType mediaType = new MediaType();
        ApiResponse apiResponse = new ApiResponse();

        mediaType.addExamples(exampleHolder.getName(), exampleHolder.getHolder());
        content.addMediaType("application/json", mediaType);
        apiResponse.content(content);
        responses.addApiResponse(String.valueOf(exampleHolder.getCode()), apiResponse);
    }

    private void addExamplesToResponses(ApiResponses responses,
        Map<Integer, List<ExampleHolder>> statusWithExampleHolders) {

        statusWithExampleHolders.forEach(
            (status, v) -> {
                Content content = new Content();
                MediaType mediaType = new MediaType();
                ApiResponse apiResponse = new ApiResponse();

                v.forEach(
                    exampleHolder -> mediaType.addExamples(
                        exampleHolder.getName(),
                        exampleHolder.getHolder()
                    )
                );
                content.addMediaType("application/json", mediaType);
                apiResponse.setContent(content);
                responses.addApiResponse(String.valueOf(status), apiResponse);
            }
        );
    }

    // ErrorResponseDto 형태의 예시 객체 생성
    private Example getSwaggerExample(ResponseType responseType) {
        com.example.tableTennis.global.response.ApiResponse<?> errorResponseDto = com.example.tableTennis.global.response.ApiResponse.create(
            responseType);
        Example example = new Example();
        example.setValue(errorResponseDto);

        return example;
    }
}
