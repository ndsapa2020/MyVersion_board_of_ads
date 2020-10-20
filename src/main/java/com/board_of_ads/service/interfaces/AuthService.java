
package com.board_of_ads.service.interfaces;

public interface AuthService {

    String vkAuth(String code);

    String yandexAuth(String code);

    String mailAuth(String code);

}