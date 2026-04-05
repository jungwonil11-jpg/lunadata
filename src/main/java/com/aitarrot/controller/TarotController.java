package com.aitarrot.controller;

import com.aitarrot.model.SpreadType;
import com.aitarrot.model.TarotCard;
import com.aitarrot.service.ClaudeService;
import com.aitarrot.service.TarotService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TarotController {

    private final TarotService tarotService;
    private final ClaudeService claudeService;

    public TarotController(TarotService tarotService, ClaudeService claudeService) {
        this.tarotService = tarotService;
        this.claudeService = claudeService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("spreadTypes", SpreadType.values());
        return "index";
    }

    @PostMapping("/draw")
    public String draw(@RequestParam String spread,
                       @RequestParam String question,
                       HttpSession session) {
        if (isMeaninglessInput(question)) {
            return "redirect:/?error=invalid";
        }

        SpreadType spreadType = SpreadType.valueOf(spread);
        List<TarotCard> drawnCards = tarotService.drawCards(spreadType);
        String interpretation = claudeService.interpret(spreadType, question, drawnCards);

        session.setAttribute("drawnCards", drawnCards);
        session.setAttribute("spreadType", spreadType);
        session.setAttribute("question", question);
        session.setAttribute("interpretation", interpretation);

        return "redirect:/result";
    }

    private boolean isMeaninglessInput(String question) {
        if (question == null || question.isBlank()) return true;

        long syllables = 0;       // 완성된 한글 음절 (가-힣)
        long consonantsVowels = 0; // 단독 자음/모음 (ㄱ-ㅎ, ㅏ-ㅣ)
        long alphanumeric = 0;     // 영문자, 숫자

        for (char c : question.toCharArray()) {
            if (c >= '\uAC00' && c <= '\uD7A3') syllables++;
            else if ((c >= '\u3131' && c <= '\u314E') || (c >= '\u314F' && c <= '\u3163')) consonantsVowels++;
            else if (Character.isLetterOrDigit(c)) alphanumeric++;
        }

        long meaningfulChars = syllables + alphanumeric;

        // 의미있는 문자가 거의 없거나, 자음/모음이 음절보다 많으면 무의미한 입력
        if (meaningfulChars < 2) return true;
        if (consonantsVowels > 0 && consonantsVowels >= syllables + alphanumeric) return true;

        return false;
    }

    @GetMapping("/result")
    public String result(Model model, HttpSession session) {
        List<TarotCard> drawnCards = (List<TarotCard>) session.getAttribute("drawnCards");
        SpreadType spreadType = (SpreadType) session.getAttribute("spreadType");
        String question = (String) session.getAttribute("question");
        String interpretation = (String) session.getAttribute("interpretation");

        if (drawnCards == null) {
            return "redirect:/";
        }

        String interpretationHtml = interpretation.replace("\n", "<br>");

        model.addAttribute("drawnCards", drawnCards);
        model.addAttribute("spreadType", spreadType);
        model.addAttribute("question", question);
        model.addAttribute("interpretation", interpretation);
        model.addAttribute("interpretationHtml", interpretationHtml);
        return "result";
    }
}
