package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.User;

public interface AuthorizationService {

    String isValid(User user);

    void login(User user);
}
