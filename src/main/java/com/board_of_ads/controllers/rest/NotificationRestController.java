package com.board_of_ads.controllers.rest;

import com.board_of_ads.models.Notification;
import com.board_of_ads.models.User;
import com.board_of_ads.service.interfaces.NotificationService;
import com.board_of_ads.service.interfaces.UserService;
import com.board_of_ads.util.Error;
import com.board_of_ads.util.ErrorResponse;
import com.board_of_ads.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/notification")
public class NotificationRestController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    public NotificationRestController(){}

    @GetMapping(value="/user", produces="application/json")
    public Response<List<Notification>> getNotificationsOfUser(@RequestParam(defaultValue="1") Long id){

        log.debug("NotificationRestController: inside getNotificationsOfUser api for fetch user id {} notification ", id);

        User user = userService.getUserById(id);

        List<Notification> notifications = notificationService.getUsersAllNotifications(user);
        return (notifications.size() > 0)
                ? Response.ok(notifications)
                : new ErrorResponse<>(new Error(204, "Notifications not found"));
    }

    @PatchMapping(value="/notification", produces="application/json")
    public Response<Object> updateUserNotification(){

        log.debug("inside updateUserNotification api ");
   //     Map<String,Object> response = notificationService.updateUserNotification(note,reqUpdateNotitfication.getUser());



        return new ErrorResponse<>(new Error(204, "Notifications not found"));
    }

}
