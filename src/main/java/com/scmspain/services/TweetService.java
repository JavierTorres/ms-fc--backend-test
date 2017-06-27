package com.scmspain.services;

import com.scmspain.entities.Tweet;
import com.scmspain.validators.Validator;
import org.springframework.boot.actuate.metrics.writer.Delta;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TweetService {
    private EntityManager entityManager;
    private MetricWriter metricWriter;
    private Validator<Tweet> validator;

    public TweetService(EntityManager entityManager, MetricWriter metricWriter, Validator<Tweet> validator) {
        this.entityManager = entityManager;
        this.metricWriter = metricWriter;
        this.validator = validator;
    }

    /**
      Push tweet to repository
      Parameter - publisher - creator of the Tweet
      Parameter - text - Content of the Tweet
      Result - recovered Tweet
    */
    public void publishTweet(String publisher, String text) throws IllegalArgumentException {
        Tweet tweet = new Tweet();
        tweet.setTweet(text);
        tweet.setPublisher(publisher);

        validateTweet(tweet);

        this.metricWriter.increment(new Delta<Number>("published-tweets", 1));
        this.entityManager.persist(tweet);
    }

    /**
      Recover tweet from repository
      Parameter - id - id of the Tweet to retrieve
      Result - retrieved Tweet
    */
    public Tweet getTweet(Long id) {
      return this.entityManager.find(Tweet.class, id);
    }

    /**
      Recover tweet from repository
      Parameter - id - id of the Tweet to retrieve
      Result - retrieved Tweet
    */
    public List<Tweet> listAllTweets() {
        List<Tweet> result = new ArrayList<Tweet>();
        this.metricWriter.increment(new Delta<Number>("times-queried-tweets", 1));
        TypedQuery<Long> query = this.entityManager.createQuery("SELECT id FROM Tweet AS tweetId WHERE pre2015MigrationStatus<>99 ORDER BY id DESC", Long.class);
        List<Long> ids = query.getResultList();
        for (Long id : ids) {
            result.add(getTweet(id));
        }
        return result;
    }

    private void validateTweet(Tweet tweet) throws IllegalArgumentException {
        this.validator.validate(tweet);
    }
}
