package com.board_of_ads.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"users"})
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

//    @ManyToMany(mappedBy = "usersNotifications" )
////    @JoinTable(name = "users_notifications",
////            joinColumns = @JoinColumn(name = "notification_id", referencedColumnName = "id"),
////            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
//    private List<User> to = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_notifications",
            joinColumns = @JoinColumn(name = "notification_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> to = new ArrayList<>();

    @Size(min = 5, max = 30)
    @Column(name="message_title")
    private String messageTitle;

    @Size(min = 5, max = 150)
    @Column(name="message_body")
    private String messageBody;

    @Size(max = 250)
    @Column(name = "click_action")
    private String clickAction = "https://www.avito.ru"; // redirect user to this URL

    @Size(max = 50)
    @Column
    private String status = "created";		// (sent / delivered / read)

    @Column(name = "created_time")
    @CreatedDate
    private LocalDateTime createdTime = LocalDateTime.now();

    public Notification(String messageTitle, String message) {
        this.messageTitle = messageTitle;
        messageBody = message;
    }

    public Notification(String messageTitle, String message, List<User> users) {
        this.messageTitle = messageTitle;
        messageBody = message;
        to = users;
    }

}
