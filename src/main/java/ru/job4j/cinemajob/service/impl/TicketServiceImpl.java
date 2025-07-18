package ru.job4j.cinemajob.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ru.job4j.cinemajob.dto.FilmDto;
import ru.job4j.cinemajob.dto.FilmSessionDto;
import ru.job4j.cinemajob.dto.TicketDto;
import ru.job4j.cinemajob.dto.TicketSaleDto;
import ru.job4j.cinemajob.model.Ticket;
import ru.job4j.cinemajob.repository.FilmRepository;
import ru.job4j.cinemajob.repository.FilmSessionRepository;
import ru.job4j.cinemajob.repository.TicketRepository;
import ru.job4j.cinemajob.service.TicketService;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final FilmSessionRepository filmSessionRepository;
    private final FilmRepository filmRepository;

    public TicketServiceImpl(TicketRepository ticketRepository,
                             FilmSessionRepository filmSessionRepository,
                             FilmRepository filmRepository) {
        this.ticketRepository = ticketRepository;
        this.filmSessionRepository = filmSessionRepository;
        this.filmRepository = filmRepository;
    }

    @Override
    @Transactional
    public String bookTicket(TicketSaleDto ticketDto, Model model) {

        if (ticketDto == null) {
            throw new IllegalArgumentException("Ticket не должен быть равен null");
        }

        if (isTicketAlreadyBooked(ticketDto)) {
            model.addAttribute("error", "Не удалось приобрести билет на заданное место. " +
                    "Вероятно оно уже занято. Выберите другой билет и попробуйте снова.");
            return getBuyTicketLinkAndData(ticketDto.getSessionId(), model);
        }

        Optional<FilmSessionDto> optionalFilmSessionDto = filmSessionRepository.findById(ticketDto.getSessionId());
        fillInfoAboutTicket(optionalFilmSessionDto, ticketDto, model);

        try {
            ticketRepository.save(new Ticket(ticketDto.getSessionId(), ticketDto.getRowNumber(),
                    ticketDto.getPlaceNumber(), ticketDto.getUserId()));
        } catch (Exception e) {
            return "tickets/errorBooking";
        }
        return "tickets/orderSuccessful";
    }

    private void fillInfoAboutTicket(Optional<FilmSessionDto> optionalFilmSessionDto, TicketSaleDto ticketDto, Model model) {
        optionalFilmSessionDto.ifPresent(filmSessionDto -> {
            model.addAttribute("rowNumber", ticketDto.getRowNumber());
            model.addAttribute("placeNumber", ticketDto.getPlaceNumber());
            model.addAttribute("sessionId", filmSessionDto.getId());
            model.addAttribute("startTime", filmSessionDto.getStartTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT)));
            String filmName = filmRepository.findById(filmSessionDto.getFilmId()).map(FilmDto::getName).orElse("");
            model.addAttribute("filmName", filmName);
        });
    }

    private boolean isTicketAlreadyBooked(TicketSaleDto ticket) {
        return ticketRepository
                .findByRowAndPlaceAndSession(ticket.getRowNumber(), ticket.getPlaceNumber(), ticket.getSessionId()) != null;

    }

    @Override
    public String getBuyTicketLinkAndData(Integer sessionId, Model model) {
        Optional<FilmSessionDto> optionalFilmSessionDto = filmSessionRepository.findById(sessionId);
        Collection<TicketDto> boughtTickets = ticketRepository.findAllBySessionId(sessionId);

        if (optionalFilmSessionDto.isEmpty()) {
            model.addAttribute("message", "Информация о фильме не найдена");
            return "errors/404";
        }
        var filmSessionDto = optionalFilmSessionDto.get();
        int[][] placesInHall = new int[filmSessionDto.getRowCount()][filmSessionDto.getPlaceCount()];
        Arrays.stream(placesInHall).forEach(row -> Arrays.fill(row, 0));

        Set<String> boughtTicketsSet = boughtTickets.stream()
                .map(ticket -> ticket.getRowNumber() + "_" + ticket.getPlaceNumber())
                .collect(Collectors.toSet());

        for (int i = 1; i <= filmSessionDto.getRowCount(); i++) {
            for (int j = 1; j <= filmSessionDto.getPlaceCount(); j++) {
                if (boughtTicketsSet.contains(i + "_" + j)) {
                    placesInHall[i-1][j-1] = 1;
                }
            }
        }
        model.addAttribute("infoSession", filmSessionDto);
        model.addAttribute("placesInHall", placesInHall);

        return "tickets/sale";
    }

}
