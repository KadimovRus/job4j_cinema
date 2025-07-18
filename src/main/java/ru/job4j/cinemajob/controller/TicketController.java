package ru.job4j.cinemajob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinemajob.dto.TicketSaleDto;
import ru.job4j.cinemajob.model.User;
import ru.job4j.cinemajob.service.TicketService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/sale")
    public String getBuyTicketPage(@RequestParam Integer sessionId, Model model) {
        return ticketService.getBuyTicketLinkAndData(sessionId, model);
    }

    @PostMapping("/sale")
    public String buyTicket(@ModelAttribute TicketSaleDto ticketDto, Model model, HttpSession session) {

        var user = (User) session.getAttribute("user");
        if (user != null) {
            ticketDto.setUserId(user.getId());
        }

        model.addAttribute("ticketInfo", ticketDto);
        return ticketService.bookTicket(ticketDto, model);
    }

}
