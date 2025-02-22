package com.example.tableTennis.domain.common.impl;

import com.example.tableTennis.global.exception.CustomException;
import com.example.tableTennis.global.response.ResponseType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class OutApiClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final String FAKER_API_URL = "https://fakerapi.it/api/v1/users?_seed=%s&_quantity=%d&_locale=ko_KR";

    public JsonNode fakerApi(int seed, int quantity) {
        String url = String.format(FAKER_API_URL, seed, quantity);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        JsonNode rootNode;
        try {
            rootNode = objectMapper.readTree(response.getBody());
        } catch (Exception e) {
            throw new CustomException(ResponseType.FAIL);
        }
        return rootNode.path("data"); // "data" 배열 가져오기
    }
}
