package com.board_of_ads.service.impl;

import com.board_of_ads.models.Notification;
import com.board_of_ads.models.User;
import com.board_of_ads.repository.NotificationRepository;
import com.board_of_ads.service.interfaces.NotificationService;
import com.board_of_ads.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private final NotificationRepository notificationRepository;

    @Autowired
    private final UserService userService;

    @Override
    public void createNotification(Notification notification) {
        log.info("In Method createNotification");
        log.info(notification.getMessageTitle());
        log.info("notification.to =" + notification.getTo().toString());
        try {
            notificationRepository.save(notification);
            log.info("a notification created!");
        } catch (Exception e) {
            log.error("Exception occur while save Notification ", e);
        }
    }

    public void sendNotificationToUsers(Notification notification, List<User> users){
        log.info("In Method send Notification To User");
        log.info(notification.getMessageTitle());
        log.info(notification.getTo().toString());

    };

    public List<Notification> getUsersAllNotifications(User user){
             log.info("Get all notifications of this {} user ", user);
        try{

          //  log.info("All notifications = " + user.getUsersNotifications());
         //  List<Notification> notes =  notificationRepository.getAll();
        List<Notification> notes =  notificationRepository.getNotificationsByToIsContaining(user);
        log.info("Called getNotifications to {} user", user.getFirsName());
            for (Notification note: notes
                 ) {
                System.out.println(" notification title = " + note.getMessageTitle());
//                List<User> users = note.getTo();
//                System.out.println("        to user:");
//                for (User userOne: users
//                     ) {
//                    System.out.println("        user " + userOne.getFirsName());
//                }
            }
         //   List<Notification> notes2 =  notificationRepository.getNotificationsByToIsContaining(user);

//            return notificationRepository.userNotifications(user.getId());
            return null;
        }catch (Exception e) {
            log.error("Exception occur while fetch Notification by User ",e);
            return null;
        }

    }

    public List<Notification> getAllNotifications(){
        log.info("Get all notifications in Data base ");
        try{

            List<Notification> notes =  notificationRepository.getAll();
            log.info("Called getAll() notifications");
            int i = 0;
            for (Notification note: notes
            ) {
                System.out.println(" notification: " + i++);
                System.out.println(" notification id " + note.getId());
                System.out.println(" title = " + note.getMessageTitle());
                System.out.println(" body = " + note.getMessageBody());
                System.out.println(" clickAction = " + note.getClickAction());
                System.out.println(" createdTime = " + note.getCreatedTime());
                System.out.println(" status = " + note.getStatus());
                System.out.println();
            }
            return notes;
        }catch (Exception e) {
            log.error("Exception occur while fetch Notification by User ",e);
            return null;
        }

    }

    public void updateUserNotificationsList(Notification notification, User user){
//        List<Notification> noteList = notificationRepository.userNotifications(user.getId());
//        noteList.add(notification);
//        notificationRepository.save(notification);
    }

    @Override
    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

}
