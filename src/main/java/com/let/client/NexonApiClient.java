package com.let.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * packageName    : com.let.client
 * fileName       : NexonApiClient
 * author         : jun
 * date           : 25. 12. 18.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 18.        jun       최초 생성
 */
@Component
public class NexonApiClient {

    private final WebClient webClient;

    public NexonApiClient(
            WebClient.Builder builder,
            @Value("${nexon.api.base-url:https://open.api.nexon.com}") String baseUrl,
            @Value("${maple.api.key}") String apiKey
    ) {
        this.webClient = builder
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                // 넥슨 Open API는 API Key 헤더를 요구합니다. (헤더명은 문서 기준으로 확인/조정)
                .defaultHeader("x-nxopen-api-key", apiKey)
                .build();
    }

    /**
     * 가장 범용적인 GET 호출 (JSON 그대로 받고 싶을 때)
     */
    public Mono<JsonNode> getJson(String path, Map<String, ?> queryParams) {
        return webClient.get()
                .uri(uriBuilder -> {
                    var b = uriBuilder.path(path);
                    if (queryParams != null) {
                        queryParams.forEach(b::queryParam);
                    }
                    return b.build();
                })
                .retrieve()
                .bodyToMono(JsonNode.class);
    }

    /**
     * DTO로 매핑해서 받고 싶을 때
     */
    public <T> Mono<T> get(String path, Map<String, ?> queryParams, Class<T> responseType) {
        return webClient.get()
                .uri(uriBuilder -> {
                    var b = uriBuilder.path(path);
                    if (queryParams != null) {
                        queryParams.forEach(b::queryParam);
                    }
                    return b.build();
                })
                .retrieve()
                .bodyToMono(responseType);
    }
}
