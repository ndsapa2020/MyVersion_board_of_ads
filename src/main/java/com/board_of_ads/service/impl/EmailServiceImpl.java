package com.board_of_ads.service.impl;

import com.board_of_ads.service.interfaces.EmailService;
import com.board_of_ads.service.interfaces.UserService;
import com.board_of_ads.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    public EmailServiceImpl(
            @Qualifier("javaMailSender") JavaMailSender javaMailSender,
            UserService userService,
            JwtTokenUtil jwtTokenUtil) {
        this.javaMailSender = javaMailSender;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Value("${spring.mail.username}")
    private String username;

    @Override
    public void sendEmail(String to, String body, String topic) {
        logger.info("Try to sending email");
        var simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(username);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(topic);
        simpleMailMessage.setText(body);
        javaMailSender.send(simpleMailMessage);
        logger.info("Email was send successfully");
    }

    @Override
    public boolean sendMailIfPresent(String to) {
        boolean result = false;
        var user = userService.getUserByEmail(to);
        if (user != null) {
            var token = jwtTokenUtil.generateToken(user);
            result = true;
            try {
                sendEmail(
                        to,
                        "http://localhost:5556/confirm/?token=" + token,
                        "Восстановление доступа к профилю"
                );
            } catch (Exception e) {
                logger.error(e.getMessage());
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean changePassword(String token, String password) {
        boolean result;
        try {
            var user = userService.getUserByEmail(jwtTokenUtil.getUsernameFromToken(token));
            user.setPassword(password);
            userService.saveUser(user);
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
            result = true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            result = false;
        }
        return result;
    }
}
