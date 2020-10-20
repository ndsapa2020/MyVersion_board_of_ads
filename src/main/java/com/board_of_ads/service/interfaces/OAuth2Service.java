package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.User;

public interface OAuth2Service {

    void auth();
    void setAuthenticated(User user);
}