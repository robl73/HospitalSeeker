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

@Entity
@Table(name = "verifications_tokens")

@NamedQueries({
        @NamedQuery(name = "GET_VERIFICATION_TOKEN_BY_NAME", query = "SELECT t FROM VerificationToken t WHERE t.token = :token"),
        @NamedQuery(name = "GET_VERIFICATION_TOKEN_BY_USER", query = "SELECT t FROM VerificationToken t WHERE t.user = :user"),
})
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "verification_token_gen")
    @SequenceGenerator(name = "verification_token_gen", sequenceName = "verification_token_gen_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name="token")
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="expirydate")
    private Date expiryDate;

    public VerificationToken() {
        super();
    }

    public VerificationToken(String token, User user, Integer verificationToken) {
        super();
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(verificationToken * 60);
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

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "VerificationToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", user=" + user +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
