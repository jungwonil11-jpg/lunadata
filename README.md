# 🔮 AI Tarot — AI 타로 리딩 웹 애플리케이션

질문을 입력하고 스프레드를 선택하면, 무작위로 뽑힌 타로 카드를 Claude AI가 해석해주는 웹 애플리케이션입니다.

## 주요 기능

- **5가지 스프레드 지원**

  | 스프레드 | 카드 수 | 설명 |
  |---|---|---|
  | 원 카드 | 1장 | 하나의 답 |
  | 쓰리 카드 | 3장 | 과거 · 현재 · 미래 |
  | 파이브 카드 | 5장 | 심층 상황 분석 |
  | 매직 세븐 | 7장 | 상대방의 속마음 |
  | 켈틱 크로스 | 10장 | 가장 심층적인 분석 |

- **78장 풀 덱** — 메이저 아르카나 22장 + 마이너 아르카나 56장 (정방향/역방향)
- **AI 해석** — 뽑힌 카드와 질문을 바탕으로 Claude가 종합 조언 + 카드별 해석 생성
- **무의미 입력 차단** — 자음/모음만 입력하는 등 의미 없는 질문은 해석 전에 차단

## 기술 스택

- Java 17 / Spring Boot 3.3
- Thymeleaf (서버 사이드 템플릿)
- [Anthropic Java SDK](https://github.com/anthropics/anthropic-sdk-java) — `claude-haiku-4-5`
- Maven

## 실행 방법

### 1. 사전 준비

- JDK 17 이상
- [Anthropic API 키](https://console.anthropic.com/)

### 2. API 키 설정

프로젝트 루트에 `.env` 파일을 생성합니다.

```
ANTHROPIC_API_KEY=sk-ant-...
```

또는 환경변수 `ANTHROPIC_API_KEY`를 직접 설정해도 됩니다.

### 3. 실행

```bash
mvn spring-boot:run
```

브라우저에서 `http://localhost:8080` 접속.

## 프로젝트 구조

```
src/main/
├── java/com/aitarrot/
│   ├── AiTarrotApplication.java      # 진입점 (.env 로드)
│   ├── controller/TarotController.java  # 라우팅, 입력 검증
│   ├── model/
│   │   ├── SpreadType.java           # 스프레드 정의 (enum)
│   │   └── TarotCard.java            # 카드 모델
│   └── service/
│       ├── TarotService.java         # 카드 셔플/드로우
│       └── ClaudeService.java        # Claude API 호출, 프롬프트 구성
└── resources/
    ├── static/images/cards/          # 카드 이미지 (RWS 1909, 퍼블릭 도메인)
    ├── static/js/tarot.js            # 카드 선택 UI
    └── templates/                    # index, result (Thymeleaf)
```

## 카드 이미지 출처

라이더-웨이트-스미스(Rider-Waite-Smith) 타로 덱 1909년판 — 퍼블릭 도메인.
