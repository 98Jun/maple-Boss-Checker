# ===== build stage =====
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle* settings.gradle* ./
COPY src ./src

RUN chmod +x ./gradlew && ./gradlew clean bootJar -x test

# ===== runtime stage =====
FROM eclipse-temurin:21-jre
WORKDIR /app

# 운영에서 주로 필요한 기본값들(필요하면 gender에서 덮어쓰기)
ENV TZ=Asia/Seoul
ENV SPRING_PROFILES_ACTIVE=prod

COPY --from=builder /app/build/libs/*.jar app.jar

# 너 로그 기준 톰캣이 8443(http)로 떠서 맞춰둠
EXPOSE 8443

# 컨테이너 실행 시 java -jar로 구동
ENTRYPOINT ["java","-jar","/app/app.jar"]