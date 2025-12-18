# 메집사 (메이플 디스코드 봇)

메이플스토리 유저들을 위한 디스코드 봇입니다.  
관세 계산, 분배금 계산, 보스 일정 알림 등을 디스코드 채널에서 편하게 처리할 수 있도록 만드는 개인 프로젝트입니다.

---

## 기능 개요

봇이 가지고 있는 명령어는 다음과 같습니다.

> 제가 가진 명령어는  
> 1. **관세 계산기** (현재 내부 DB 사용하여 메이플포인트 시세 수집)  
> 2. **분배금 계산기** 
> 3. **보스 일정 알리미** 
> 4. **간단한 캐릭터 정보 조회** (개발예정)
> 입니다.

### 1. 관세 계산기 

- 아이템 금액(억 단위)과 메포 시세를 입력 받아,
  - 충전해야 할 메이플 포인트
  - 필요한 메소 (억 단위)
  - 동일 금액의 서버 내 아이템 금액
  를 계산해서 한 번에 알려줍니다.
- 현재는 h2 내부 데이터 베이으로 메포 시세를 입력하지만,
  - “메포 시세 미입력 시, 최근 조회값 사용”
  - 추후 스케줄러로 시세 자동 수집 계획

### 2. 분배금 계산기 

- 레이드/보스에서 드랍된 아이템 금액과 파티원 수를 입력하면
  - 1인당 분배금
  - 수수료 포함 기준 분배
  등을 계산해주는 기능

### 3. 보스 일정 알리미 

- 주간 보스 리셋 시간 기준으로
  - 특정 요일/시간에 디스코드 채널로 보스 일정을 등록하고 해당 일자 2시간 이내에 디스코드 멘션 댓글 기록
- 향후에는 “내가 체크한 보스 목록” 기준으로
  - 아직 안 깬 보스 리스트만 알려주는 기능까지 확장 계획(nexon api 제공 시)

---

## 기술 스택

- **Language**
  - Java 21
- **Framework**
  - Spring Boot 3.4.1
  - Spring Web / WebFlux 
  - Spring Scheduling (보스 일정 알리미용 스케줄러)
- **Discord**
  - JDA 6.1.3 (Java Discord API)
  - Gateway Intents
    - `GUILD_MESSAGES`, `DIRECT_MESSAGES`, `MESSAGE_CONTENT` 등 사용
- **빌드/의존성 관리**
  - Gradle
- **구동 환경**
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
