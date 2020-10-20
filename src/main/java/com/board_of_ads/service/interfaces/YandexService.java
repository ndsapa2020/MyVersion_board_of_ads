package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.User;

import java.util.Map;

public interface YandexService {

    void auth(String code);

    String getAuthURL();

    String getRequestBody(String code);

    String getToken(String body);

    Map<String, String> getUserData(String token);

    void login(User user);

    User init(Map<String, String> userData);
}
