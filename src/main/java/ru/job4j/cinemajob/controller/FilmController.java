package ru.job4j.cinemajob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinemajob.dto.FilmDto;
import ru.job4j.cinemajob.service.FilmService;

import java.util.Optional;

@Controller
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("films", filmService.findAll());
        return "films/list";
    }

    @GetMapping("/{id}")
    public String getFilmById(Model model, @PathVariable int id) {
        Optional<FilmDto> filmDto = filmService.findById(id);
        if (filmDto.isEmpty()) {
            model.addAttribute("message", "Данный фильм не найден");
            return "errors/404";
        }
        model.addAttribute("film", filmDto.get());
        return "films/film";
    }

}
