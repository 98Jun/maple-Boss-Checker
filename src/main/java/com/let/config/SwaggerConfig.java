package com.let.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.let.monitoring.config
 * fileName       : OpenApiConfig
 * author         : jun
 * date           : 25. 12. 9.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 9.        jun       최초 생성
 */

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI serverMonitoringOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Maple Checker API")
                        .description("메이플 봇 사용에 필요한 디스코드 채널 등록 API")
                        .version("v1.0.0"));

    }
}
