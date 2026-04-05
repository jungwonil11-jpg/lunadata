package com.aitarrot.model;

import java.util.List;

public enum SpreadType {
    ONE(1, "원 카드", "하나의 답", List.of("현재")),
    THREE(3, "쓰리 카드", "과거 · 현재 · 미래", List.of("과거", "현재", "미래")),
    FIVE(5, "파이브 카드", "심층 상황 분석", List.of("과거", "현재", "조언", "미래", "핵심 메시지")),
    SEVEN(7, "매직 세븐", "상대방의 속마음", List.of("과거", "현재", "미래", "조언/해결책", "주변환경", "대립하는것", "결과")),
    TEN(10, "켈틱 크로스", "가장 심층적인 분석", List.of(
            "현재 상황", "도전/장애물", "먼 과거", "가까운 과거",
            "가능한 미래", "가까운 미래", "자신의 태도", "외부 환경",
            "희망과 두려움", "최종 결과"));

    private final int cardCount;
    private final String displayName;
    private final String description;
    private final List<String> positions;

    SpreadType(int cardCount, String displayName, String description, List<String> positions) {
        this.cardCount = cardCount;
        this.displayName = displayName;
        this.description = description;
        this.positions = positions;
    }

    public int getCardCount() { return cardCount; }
    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
    public List<String> getPositions() { return positions; }
}
