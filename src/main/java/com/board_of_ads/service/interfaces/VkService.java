package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.User;

import java.util.Map;

public interface VkService {

    void auth(String code);

    String getAuthURL();

    String getAuthResponseURL(String code);

    Map<String, String> getUserData(String authResponseUrl);

    Map<String, String> getUserData(Map<String, String> userData);

    void login(User user);

    User init(Map<String, String> userData);
}
