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
        SpreadType spreadType = SpreadType.valueOf(spread);
        List<TarotCard> drawnCards = tarotService.drawCards(spreadType);
        String interpretation = claudeService.interpret(spreadType, question, drawnCards);

        session.setAttribute("drawnCards", drawnCards);
        session.setAttribute("spreadType", spreadType);
        session.setAttribute("question", question);
        session.setAttribute("interpretation", interpretation);

        return "redirect:/result";
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
