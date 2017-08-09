package com.scmspain.api;

import com.scmspain.entity.Tweet;

import java.util.List;

/**
 * Created by Javier Bracero Torres on 09/08/2017.
 */
public interface TweetServiceApi {

    /**
     * Publish a Tweet
     * @param tweet to publish
     */
    Tweet publishTweet(Tweet tweet);

    /**
     * Get a tweet by id
     * @param id
     * @return the found tweet
     */
    Tweet getTweet(Long id);

    /**
     * Discard a Tweet by id.
     * @param id
     */
    void discardTweet(Long id);

    /**
     * Return all the tweets.
     * @return a list of Tweets
     */
    List<Tweet> listAllTweets();

    /**
     * Return all the discarded tweets.
     * @return a list of discarded tweets
     */
    List<Tweet> listDiscardedTweets();
}
