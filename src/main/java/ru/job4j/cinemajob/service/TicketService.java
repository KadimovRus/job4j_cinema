package ru.job4j.cinemajob.service;

import org.springframework.ui.Model;
import ru.job4j.cinemajob.dto.TicketSaleDto;

public interface TicketService {

    public String bookTicket(TicketSaleDto ticketDto, Model model);

    public String getBuyTicketLinkAndData(Integer sessionId, Model model);
}
