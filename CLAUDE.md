# AiTarrot (lunadata)

AI 타로 리딩 웹 애플리케이션. 사용자가 질문을 입력하고 스프레드를 고르면 카드를 뽑아 Claude가 해석해준다.

## 스택

- Java 17 / Spring Boot 3.3.0 / Maven
- Thymeleaf (SSR) + vanilla JS/CSS (`static/`)
- Anthropic Java SDK 2.17.0 — 모델 `claude-haiku-4-5` (`ClaudeService.MODEL`)
- DB 없음. 결과는 HttpSession에만 저장

## 실행

1. 프로젝트 루트에 `.env` 필요: `ANTHROPIC_API_KEY=sk-ant-...`
   - `AiTarrotApplication.main()`에서 dotenv-java가 로드 → 시스템 프로퍼티 주입 → `application.properties`의 `${ANTHROPIC_API_KEY:}`가 받음
   - `.env`는 gitignore 대상. 절대 커밋 금지
2. IntelliJ에서 `AiTarrotApplication` 실행 또는 `mvn spring-boot:run`
3. http://localhost:8080

## 구조

```
src/main/java/com/aitarrot/
├── AiTarrotApplication.java   # dotenv 로드 + 부트스트랩
├── controller/TarotController.java  # / (index), POST /draw, /result
├── model/
│   ├── SpreadType.java        # enum: ONE/THREE/FIVE/SEVEN/TEN (카드 수·위치명 정의)
│   └── TarotCard.java
└── service/
    ├── TarotService.java      # 카드 뽑기 (셔플 + 정/역방향)
    └── ClaudeService.java     # 해석 요청 (시스템 프롬프트 + 출력 형식 정의)

src/main/resources/
├── application.properties     # 포트 8080, API 키 바인딩
├── static/js/tarot.js         # 스프레드별 힌트(spreadHints) + 프론트 로직
├── static/images/cards/       # 타로 카드 78장 이미지
└── templates/                 # index.html, result.html (Thymeleaf)
```

## 핵심 흐름

`POST /draw` → 무의미 입력 차단(`isMeaninglessInput`: 한글 음절/자모 비율 검사) → `TarotService.drawCards()` → `ClaudeService.interpret()` → 세션 저장 → `/result` 리다이렉트

## 컨벤션

- 해석 출력 형식(✦ 조언 → 카드별 해석 → ✦ 종합 의견)은 `ClaudeService.SYSTEM_PROMPT`에서 관리
- 스프레드별 예시 질문·힌트 문구는 `tarot.js`의 `spreadHints` 객체에서만 수정
- 스프레드 추가 시 `SpreadType` enum + `spreadHints` 양쪽 모두 갱신 필요
