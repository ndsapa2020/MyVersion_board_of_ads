package com.board_of_ads.util;

import com.board_of_ads.models.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@Table(name = "recovery_token")
public class PasswordRecoveryToken {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column
    private String hash;

    @Column
    private String hashEmail;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date startTime;

    public PasswordRecoveryToken(User user, String hash, String hashEmailToken, Date startTime) {
        this.user = user;
        this.hash = hash;
        this.hashEmail = hashEmailToken;
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordRecoveryToken that = (PasswordRecoveryToken) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(hash, that.hash) &&
                Objects.equals(hashEmail, that.hashEmail) &&
                Objects.equals(startTime, that.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, hash, hashEmail, startTime);
    }
}
