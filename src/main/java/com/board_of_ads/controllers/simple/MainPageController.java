package com.board_of_ads.controllers.simple;

import com.board_of_ads.models.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
@Slf4j
public class MainPageController {

    @GetMapping("/")
    public String getMainPage(@AuthenticationPrincipal() User user, Model model) {
        log.info("Use this default logger");
        model.addAttribute("user", user != null ? user : new User());
        return "main-page";
    }

    @GetMapping("/admin_page")
    public String adminPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute(user);
        return "admin_page";
    }

    @GetMapping("/new_post")
    public String addNewPost(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user != null ? user : new User());
        return "newpost-page";
    }

    @GetMapping("/profile")
    public String profilePage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute(user);
        return "profile";
    }

    @GetMapping("/confirm/")
    public String confirmPassword() {
        return "main-page";
    }
}