package com.scmspain.repository;

import com.scmspain.entity.Tweet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

/**
 * Created by Javier Bracero Torres on 09/08/2017.
 */
public interface TweetRepository extends CrudRepository<Tweet, Long> {
    @Query("select t from Tweet t order by t.publishedAt desc ")
    Stream<Tweet> getAllOrderedByPusblishedAt();

    @Query("select t from Tweet t where t.discardedAt is not null order by t.discardedAt desc")
    Stream<Tweet> getAllDiscarded();
}
