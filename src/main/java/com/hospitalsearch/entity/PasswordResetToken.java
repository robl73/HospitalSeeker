package com.hospitalsearch.entity;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;


/**
 * @author Andrew Jasinskiy on 20.06.16
 */

@NamedQueries({
        @NamedQuery(name = "GET_RESET_PASSWORD_TOKEN_BY_NAME", query = "SELECT p FROM PasswordResetToken p WHERE p.token = :token"),
        @NamedQuery(name = "GET_RESET_PASSWORD_TOKEN_BY_USER", query = "SELECT p FROM PasswordResetToken p WHERE p.user = :user"),
})

@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken {
	
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "password_reset_token_gen")
    @SequenceGenerator(name = "password_reset_token_gen", sequenceName = "password_reset_token_gen_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "token")
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expirydate")
    private Date expiryDate;

    public PasswordResetToken() {
        super();
    }

    public PasswordResetToken(String token, User user, Integer RESET_PASSWORD_TOKEN_EXPIRATION) {
        super();
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(RESET_PASSWORD_TOKEN_EXPIRATION * 60);
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "PasswordResetToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", user=" + user +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
