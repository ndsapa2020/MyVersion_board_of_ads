package com.board_of_ads.controllers.simple;

import com.board_of_ads.service.interfaces.MailService;
import com.board_of_ads.service.interfaces.OAuth2Service;
import com.board_of_ads.service.interfaces.OkService;
import com.board_of_ads.service.interfaces.VkService;
import com.board_of_ads.service.interfaces.YandexService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/social")
@Slf4j
public class AuthController {

    private final YandexService yandexService;
    private final VkService vkService;
    private final OAuth2Service OAuth2Service;
    private final MailService mailService;
    private final OkService okService;

    @GetMapping("/auth")
    public String auth() {
        log.info("Use this default logger");
        OAuth2Service.auth();
        return "redirect:/";
    }

    @GetMapping("/auth_facebook")
    public String facebookAuth() {
        return "redirect:/oauth2/authorization/facebook";
    }

    @GetMapping("/auth_google")
    public String googleAuth() {
        return "redirect:/oauth2/authorization/google";
    }

    @GetMapping("/auth_vk")
    public String vkAuth(@RequestParam(value = "code", required = false) String code, Model model) {
        if (code == null) {
            return "redirect:" + vkService.getAuthURL();
        }
        vkService.auth(code);
        return "redirect:/";
    }

    @GetMapping("/auth_yandex")
    public String yandexAuth(@RequestParam(value = "code", required = false) String code, Model model) {
        if (code == null) {
            return "redirect:" + yandexService.getAuthURL();
        }
        yandexService.auth(code);
        return "redirect:/";
    }

    @GetMapping("/auth_ok")
    public String okAuth(@RequestParam(value = "code", required = false) String code, Model model) {
        if (code == null) {
            return "redirect:" + okService.getAuthURL();
        }
        okService.auth(code);
        return "redirect:/";
    }

    @GetMapping("/mail_auth")
    public String mailAuth(@RequestParam(value = "code", required = false) String code) {
        if (code == null) {
            return "redirect:" + mailService.getAuthURL();
        }
        mailService.auth(code);
        return "redirect:/";
    }
}
