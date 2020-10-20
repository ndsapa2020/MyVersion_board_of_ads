package com.board_of_ads.service.interfaces;

public interface EmailService {

    void sendEmail(String to, String body, String topic);

    boolean sendMailIfPresent(String mail);

    boolean changePassword(String token, String password);
}
