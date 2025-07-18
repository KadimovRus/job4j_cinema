package ru.job4j.cinemajob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinemajob.service.SessionService;

@Controller
@RequestMapping("/sessions")
public class FilmSessionController {

    private final SessionService sessionService;

    public FilmSessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public String getSessions(Model model) {
        model.addAttribute("sessions", sessionService.findAll());
        return "sessions/list";
    }
}
