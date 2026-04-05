package com.aitarrot.service;

import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.aitarrot.model.SpreadType;
import com.aitarrot.model.TarotCard;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaudeService {

    private static final String MODEL = "claude-haiku-4-5";

    private static final String SYSTEM_PROMPT =
        "당신은 따뜻하고 친절한 여성 타로 리더입니다. 반드시 한국어로, 부드럽고 친근한 여성형 말투(~해요, ~이에요, ~네요)로 답변하세요.\n\n" +
        "[형식 — 반드시 이 순서를 지켜주세요]\n" +
        "1. 먼저:\n" +
        "✦ 조언\n" +
        "전체 카드의 흐름을 바탕으로 한 핵심 조언을 3~4문장으로 작성\n\n" +
        "2. 그 다음 카드별 해석:\n" +
        "▪ [위치명] 카드명 (정/역방향)\n" +
        "2~3문장 이내로 간결하게\n\n" +
        "[규칙]\n" +
        "- 조언이 항상 맨 앞에 와야 해요\n" +
        "- 카드별 해석은 짧고 핵심만\n" +
        "- 서론·인사말 금지\n" +
        "- 형식 외 추가 섹션 금지";

    private final AnthropicClient client;

    public ClaudeService(@Value("${anthropic.api.key}") String apiKey) {
        this.client = AnthropicOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
    }

    public String interpret(SpreadType spreadType, String question, List<TarotCard> cards) {
        String userPrompt = buildPrompt(spreadType, question, cards);

        MessageCreateParams params = MessageCreateParams.builder()
                .model(MODEL)
                .maxTokens(1500L)
                .system(SYSTEM_PROMPT)
                .addUserMessage(userPrompt)
                .build();

        Message response = client.messages().create(params);

        return response.content().stream()
                .flatMap(block -> block.text().stream())
                .map(textBlock -> textBlock.text())
                .findFirst()
                .orElse("해석을 가져오지 못했습니다. 잠시 후 다시 시도해주세요.");
    }

    private String buildPrompt(SpreadType spreadType, String question, List<TarotCard> cards) {
        StringBuilder sb = new StringBuilder();
        sb.append("다음 타로 스프레드를 해석해주세요.\n\n");
        sb.append("질문: ").append(question).append("\n");
        sb.append("스프레드: ").append(spreadType.getDisplayName())
          .append(" (").append(spreadType.getDescription()).append(")\n\n");
        sb.append("뽑힌 카드:\n");

        for (TarotCard card : cards) {
            sb.append(String.format("- [%s] %s (%s)\n",
                    card.getPosition(), card.getNameKo(), card.getOrientationKo()));
        }

        sb.append("\n위 형식에 따라 각 카드를 간결하게 해석하고, 마지막에 종합 의견을 작성해주세요.");

        return sb.toString();
    }
}
