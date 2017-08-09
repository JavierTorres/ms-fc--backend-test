package com.scmspain.dto;

import com.scmspain.entity.Tweet;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Javier Bracero Torres on 09/08/2017.
 */
public class PublishTweetDto {
    @NotEmpty
    private String publisher;

    @NotEmpty
    private String tweet;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public Tweet toTweet() {
        return new Tweet().setTweet(tweet).setPublisher(publisher);
    }
}
