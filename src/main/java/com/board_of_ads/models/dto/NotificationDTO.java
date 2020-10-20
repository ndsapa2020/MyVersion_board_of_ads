package com.board_of_ads.models.dto;

import com.board_of_ads.models.User;

import java.time.LocalDateTime;
import java.util.List;

public class NotificationDTO {
    private Long id;
 //   private List<User> to;
    private String messageTitle;
    private String messageBody;
    private String clickAction;
    private String group;
    private String status;
    private LocalDateTime createdTime;

    public NotificationDTO(Long id, String messageTitle, String messageBody, String status) {
        this.id = id;
        this.messageTitle = messageTitle;
        this.messageBody = messageBody;
        this.status = status;
    }

}
