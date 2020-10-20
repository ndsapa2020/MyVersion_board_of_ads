package com.board_of_ads.service.interfaces;

import com.board_of_ads.models.Notification;
import com.board_of_ads.models.User;

import java.util.List;
import java.util.Optional;

public interface NotificationService {
    void createNotification(Notification notification);
    List<Notification> getUsersAllNotifications(User user);
    List<Notification> getAllNotifications();
    void updateUserNotificationsList(Notification notification, User user);
    void sendNotificationToUsers(Notification notification, List<User> users);
    Optional<Notification> getNotificationById(Long id);
}
