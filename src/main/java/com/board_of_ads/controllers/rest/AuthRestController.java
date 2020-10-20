package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.User;
import com.board_of_ads.service.interfaces.AuthorizationService;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.ErrorResponse;
import com.board_of_ads.util.Response;
import com.board_of_ads.util.SuccessResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class AuthRestController {

    private final AuthorizationService authorizationService;

    @PostMapping()
    public Response<?> authorization(@RequestBody User userAuth) {
        log.info("Use this default logger");
        String authMessage = authorizationService.isValid(userAuth);
        return (authMessage.equals("OK"))
                ? new SuccessResponse<>()
                : new ErrorResponse<>(new Error(401, authMessage));
    }
}
