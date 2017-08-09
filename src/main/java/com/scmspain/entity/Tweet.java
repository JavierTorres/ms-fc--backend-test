package com.scmspain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Tweet {
    private static final String LINK_TEXT = "(https?|http)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotEmpty
    private String publisher;

    @Column
    @NotEmpty
    private String tweet;

    @Column
    private Long pre2015MigrationStatus = 0L;

    @CreatedDate
    @JsonIgnore
    LocalDateTime publishedAt;

    @JsonIgnore
    LocalDateTime discardedAt;

    public Tweet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public Tweet setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public String getTweet() {
        return tweet;
    }

    public Tweet setTweet(String tweet) {
        this.tweet = tweet;
        if (tweet.replaceAll(LINK_TEXT, "").length() > 140) {
            throw new IllegalArgumentException("The tweet length is bigger than the maximum limit");
        }
        return this;
    }

    public Long getPre2015MigrationStatus() {
        return pre2015MigrationStatus;
    }

    public Tweet setPre2015MigrationStatus(Long pre2015MigrationStatus) {
        this.pre2015MigrationStatus = pre2015MigrationStatus;
        return this;
    }

    public LocalDateTime getDiscardedAt() {
        return discardedAt;
    }

    public Tweet setDiscardedAt(LocalDateTime discardedAt) {
        this.discardedAt = discardedAt;
        return this;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public Tweet setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
        return this;
    }
}
