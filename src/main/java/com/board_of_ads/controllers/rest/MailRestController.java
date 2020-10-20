package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.User;
import com.board_of_ads.service.interfaces.EmailService;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.ErrorResponse;
import com.board_of_ads.util.Response;
import com.board_of_ads.util.SuccessResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
@AllArgsConstructor
@Slf4j
public class MailRestController {

    private final EmailService emailService;

    @PutMapping
    public Response<?> changePassword(@RequestParam("token") String token, @RequestBody User user) {
        log.info("Use this default logger");
        return emailService.changePassword(token, user.getPassword())
                ? new SuccessResponse<>()
                : new ErrorResponse<>(new Error(304, "No change password"));
    }

    @GetMapping("/{email}")
    public Response<?> sendEmail(@PathVariable String email) {
        return emailService.sendMailIfPresent(email)
                ? new SuccessResponse<>()
                : new ErrorResponse<>(new Error(304, "No send email"));
    }
}
