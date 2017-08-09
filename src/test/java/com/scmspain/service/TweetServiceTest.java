package com.scmspain.services;

import com.scmspain.entity.Tweet;
import com.scmspain.repository.TweetRepository;
import com.scmspain.service.TweetService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;

import java.util.stream.Stream;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TweetServiceTest {
    @Mock
    private MetricWriter metricWriter;

    private TweetService tweetService;

    @Mock
    private TweetRepository tweetRepository;

    @Before
    public void setUp() throws Exception {
        this.tweetService = new TweetService(metricWriter, tweetRepository);
    }

    @Test
    public void shouldPublishTweet() throws Exception {
        tweetService.publishTweet(new Tweet().setPublisher("Guybrush Threepwood").setTweet("I am Guybrush Threepwood, mighty pirate."));
        verify(tweetRepository).save(any(Tweet.class));
        verify(metricWriter).increment(any());
    }

    @Test
    public void shouldDiscardATweet() throws Exception {
        when(tweetService.getTweet(anyLong())).thenReturn(new Tweet());
        tweetService.discardTweet(1L);
        verify(tweetRepository).save(any(Tweet.class));
        verify(metricWriter).increment(any());
    }

    @Test
    public void shouldListAllTweets() throws Exception {
        when(tweetRepository.getAllOrderedByPusblishedAt()).thenReturn(Stream.empty());
        tweetService.listAllTweets();
        verify(tweetRepository).getAllOrderedByPusblishedAt();
        verify(metricWriter).increment(any());
    }

    @Test
    public void shouldDiscardedTweets() throws Exception {
        when(tweetRepository.getAllDiscarded()).thenReturn(Stream.empty());
        tweetService.listDiscardedTweets();
        verify(tweetRepository).getAllDiscarded();
        verify(metricWriter).increment(any());
    }
}
