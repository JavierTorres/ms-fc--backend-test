package com.scmspain.controller;

import com.scmspain.api.TweetServiceApi;
import com.scmspain.dto.DiscardTweetDto;
import com.scmspain.dto.PublishTweetDto;
import com.scmspain.entity.Tweet;
import com.scmspain.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/tweet")
public class TweetController {
    private TweetServiceApi tweetService;

    @Autowired
    public TweetController(TweetServiceApi tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping
    public List<Tweet> listAllTweets() {
        return this.tweetService.listAllTweets();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Tweet publishTweet(@RequestBody @Valid PublishTweetDto publishTweetDto) {
        return tweetService.publishTweet(publishTweetDto.toTweet());
    }

    @PostMapping(path = "/discarded")
    @ResponseStatus(NO_CONTENT)
    public void discard(@RequestBody @Valid DiscardTweetDto discardTweetDto) {
        tweetService.discardTweet(discardTweetDto.getTweet());
    }

    @GetMapping(path = "/discarded")
    public List<Tweet> listDiscardedTweets() {
        return this.tweetService.listDiscardedTweets();
    }
}
