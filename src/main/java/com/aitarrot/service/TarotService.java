package com.aitarrot.service;

import com.aitarrot.model.SpreadType;
import com.aitarrot.model.TarotCard;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class TarotService {

    private static final List<TarotCard> ALL_CARDS = new ArrayList<>();
    private static final Random RANDOM = new Random();

    static {
        // 메이저 아르카나 (22장)
        String[] majorNames = {
            "바보", "마법사", "여사제", "여황제", "황제",
            "교황", "연인", "전차", "힘", "은둔자",
            "운명의 수레바퀴", "정의", "매달린 사람", "죽음", "절제",
            "악마", "탑", "별", "달", "태양",
            "심판", "세계"
        };
        for (int i = 0; i < majorNames.length; i++) {
            ALL_CARDS.add(new TarotCard(String.format("major_%02d", i), majorNames[i]));
        }

        // 마이너 아르카나 (56장)
        String[][] suits = {
            {"minor_cups", "컵"},
            {"minor_wands", "완즈"},
            {"minor_swords", "소드"},
            {"minor_pentacles", "펜타클"}
        };
        String[] ranks = {
            "에이스", "2", "3", "4", "5", "6", "7",
            "8", "9", "10", "페이지", "나이트", "퀸", "킹"
        };
        for (String[] suit : suits) {
            for (int i = 1; i <= 14; i++) {
                String id = String.format("%s_%02d", suit[0], i);
                String name = suit[1] + " " + ranks[i - 1];
                ALL_CARDS.add(new TarotCard(id, name));
            }
        }
    }

    public List<TarotCard> drawCards(SpreadType spreadType) {
        List<TarotCard> deck = new ArrayList<>(ALL_CARDS);
        Collections.shuffle(deck);

        List<String> positions = spreadType.getPositions();
        List<TarotCard> drawn = new ArrayList<>();

        for (int i = 0; i < spreadType.getCardCount(); i++) {
            TarotCard card = deck.get(i);
            card.setReversed(RANDOM.nextBoolean());
            card.setPosition(positions.get(i));
            drawn.add(card);
        }
        return drawn;
    }

    public String getMockInterpretation(SpreadType spreadType, String question) {
        return "AI 타로 해석 기능은 곧 연동됩니다. " +
               "현재는 카드 배치와 UI를 확인하는 단계입니다. " +
               "Claude Haiku API 연동 후 '" + question + "'에 대한 심층적인 해석을 제공할 예정입니다.";
    }
}
