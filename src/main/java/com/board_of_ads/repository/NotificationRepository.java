package com.board_of_ads.repository;

import com.board_of_ads.models.Notification;
import com.board_of_ads.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> getNotificationsByToIsContaining(User user);
    @Query("select n from Notification n  ORDER BY n.createdTime DESC")
    List<Notification> getAll();
    Optional<Notification> findById(Long id);
}