package com.board_of_ads;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;


@SpringBootApplication
@Slf4j
public class BoardOfAdsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardOfAdsApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onStart() {
        log.info("Successful application launch");
    }

}
