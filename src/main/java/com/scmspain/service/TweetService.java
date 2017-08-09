package com.scmspain.service;

import com.scmspain.api.TweetServiceApi;
import com.scmspain.entity.Tweet;
import com.scmspain.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.writer.Delta;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class TweetService implements TweetServiceApi {
    private MetricWriter metricWriter;
    private TweetRepository tweetRepository;

    @Autowired
    public TweetService(MetricWriter metricWriter, TweetRepository tweetRepository) {
        this.metricWriter = metricWriter;
        this.tweetRepository = tweetRepository;
    }

    @Override
    public Tweet publishTweet(Tweet tweet) {
        metricWriter.increment(new Delta<Number>("published-tweets", 1));
        return tweetRepository.save(tweet.setPublishedAt(LocalDateTime.now()));
    }

    @Override
    public Tweet getTweet(Long id) {
        return tweetRepository.findOne(id);
    }

    @Override
    public void discardTweet(Long id) {
        if (getTweet(id) == null) {
            throw new IllegalArgumentException("Tweet not found");
        }
        metricWriter.increment(new Delta<Number>("discarded-tweets", 1));
        tweetRepository.save(getTweet(id).setDiscardedAt(LocalDateTime.now()));
    }

    @Override
    public List<Tweet> listAllTweets() {
        this.metricWriter.increment(new Delta<Number>("times-queried-tweets", 1));
        return tweetRepository.getAllOrderedByPusblishedAt().collect(Collectors.toList());
    }

    @Override
    public List<Tweet> listDiscardedTweets() {
        this.metricWriter.increment(new Delta<Number>("times-queried-discarded-tweets", 1));
        return tweetRepository.getAllDiscarded().collect(Collectors.toList());
    }
}
