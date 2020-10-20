package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.User;

import java.util.Map;

public interface MailService {

    public void auth(String code);

    public String getAuthURL();

    public String getAuthResponseURL(String code);

    public String getToken(String body);

    Map<String, String> getUserData(String token, String sig);

    public User init(Map<String, String> userData);

    public void login(User user);
}
