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
# syntax=docker/dockerfile:1.7

# ===== build stage =====
FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /app

# 1) Gradle 관련 파일만 먼저 복사해서 의존성 캐시 레이어를 최대한 살림
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle* settings.gradle* gradle.properties* ./

RUN chmod +x ./gradlew

# 2) (BuildKit 사용 시) Gradle 캐시를 마운트해서 빌드 시간을 크게 줄임
#    BuildKit을 못 쓰는 환경이면 '--mount=type=cache,...' 부분만 지우고 그대로 사용 가능
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew --no-daemon -x test dependencies

# 3) 소스는 마지막에 복사 (소스 변경 시 의존성 다운로드 레이어가 깨지지 않게)
COPY src/ src/

# 4) Jar 빌드 (clean은 컨테이너에선 의미가 거의 없고 시간만 늘려서 제거)
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew --no-daemon -x test bootJar


# ===== runtime stage =====
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# 운영에서 주로 필요한 기본값들(필요하면 gender에서 덮어쓰기)
ENV TZ=Asia/Seoul \
    SPRING_PROFILES_ACTIVE=prod \
    # 메모리 작은 무료 플랜에서 안정적으로 돌도록 JVM 메모리 비율 기본값 세팅
    JAVA_TOOL_OPTIONS="-Duser.timezone=Asia/Seoul -Dfile.encoding=UTF-8 -XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=25.0"

COPY --from=builder /app/build/libs/*.jar app.jar

# 너 로그 기준 톰캣이 8443(http)로 떠서 맞춰둠
EXPOSE 8443

# 컨테이너 실행 시 java -jar로 구동
ENTRYPOINT ["java","-jar","/app/app.jar"]