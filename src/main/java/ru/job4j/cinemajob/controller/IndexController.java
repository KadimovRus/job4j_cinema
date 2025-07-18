package ru.job4j.cinemajob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinemajob.model.User;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @GetMapping({"/", "/index"})
    public String getIndexPage(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setFullName("Гость");
        }
        model.addAttribute("user", user);
        return "index";
    }
}
