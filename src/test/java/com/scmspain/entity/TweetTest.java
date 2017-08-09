package com.scmspain.entity;

import org.junit.Test;

public class TweetTest {

    @Test(expected = IllegalArgumentException.class)
    public void setTweetLongerThanMax() {
        new Tweet().setTweet("123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 " +
                "123456789 123456789 123456789 123456789 123456789 http://www.google.com 123456789 a");
    }

    @Test
    public void setTweetOk() {
        new Tweet().setTweet("123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 " +
                "123456789 123456789 123456789 123456789 123456789 http://www.google.com http://www.google.com");
    }
}
