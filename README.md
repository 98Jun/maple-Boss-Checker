# Maple Boss Checker (메이플 디스코드 봇)

메이플스토리 유저들을 위한 디스코드 봇입니다.  
관세 계산, 분배금 계산, 보스 일정 알림 등을 디스코드 채널에서 편하게 처리할 수 있도록 만드는 개인 프로젝트입니다.

현재는 **관세 계산기** 기능을 우선 구현 중이며,  
차후 **내부 DB와 메이플 포인트 시세 수집 로직**을 붙여서 자동화/고도화할 예정입니다.

---

## 기능 개요

봇이 가지고 있는 명령어는 다음과 같습니다.

> 제가 가진 명령어는  
> 1. **관세 계산기** (고도화 예정 – 내부 DB 사용하여 메이플포인트 시세 수집)  
> 2. **분배금 계산기** (개발 예정)  
> 3. **보스 일정 알리미** (개발 예정)  
> 입니다.

### 1. 관세 계산기 (구현 중)

- 아이템 금액(억 단위)과 메포 시세를 입력 받아,
  - 충전해야 할 메이플 포인트
  - 필요한 메소 (억 단위)
  - 동일 금액의 서버 내 아이템 금액
  를 계산해서 한 번에 알려줍니다.
- 현재는 수동으로 메포 시세를 입력하지만,
  - “메포 시세 미입력 시, 최근 조회값 사용” 형태로 확장 예정
  - 나중에는 내부 DB + 스케줄러로 시세 자동 수집 계획

### 2. 분배금 계산기 (개발 예정)

- 레이드/보스에서 드랍된 아이템 금액과 파티원 수를 입력하면
  - 1인당 분배금
  - 세금/관세 포함/미포함 기준 분배
  등을 계산해주는 기능을 추가할 예정입니다.

### 3. 보스 일정 알리미 (개발 예정)

- 주간/일간 보스 리셋 시간 기준으로
  - 특정 요일/시간에 디스코드 채널로 보스 리셋 알림을 보내는 기능
- 향후에는 “내가 체크한 보스 목록” 기준으로
  - 아직 안 깬 보스 리스트만 알려주는 기능까지 확장 계획

---

## 기술 스택

- **Language**
  - Java 21
- **Framework**
  - Spring Boot 3.x
  - Spring Web / WebFlux (WebClient 사용)
  - Spring Scheduling (보스 일정 알리미용 스케줄러 예정)
- **Discord**
  - JDA 6.x (Java Discord API)
  - Gateway Intents
    - `GUILD_MESSAGES`, `DIRECT_MESSAGES`, `MESSAGE_CONTENT` 등 사용
- **빌드/의존성 관리**
  - Gradle
- **구동 환경**
  - 장기 실행되는 Spring Boot 애플리케이션
  - 디스코드 봇 프로세스를 스프링 컨텍스트에서 관리

---
## 메모
-   **디스코드 토큰 및 Maple API Key 는 환경변수로 사용 중**
---
## 주요 구조

대략적인 패키지/역할은 아래와 같습니다.

```text
src/main/java
 ├─ com
 │   └─ let
 │       ├─ MapleWeeklyBossBotApplication  // Spring Boot 메인 클래스
 │       ├─ config
 │       │    └─ DiscordBotConfig         // JDA, GatewayIntent, 리스너 등록
 │       ├─ event
 │       │    ├─ MapleNameResponseEvent   // 일반 메시지 리스너 (!명령어 등)
 │       │    └─ SlashComandEvent         // 슬래시 명령어 처리
 │       └─ controller / service (확장 예정)
 └─ resources
     └─ application.yml                   // 공통 설정 (토큰은 env 참조)
