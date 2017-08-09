package com.scmspain.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by Javier Bracero Torres on 09/08/2017.
 */
public class DiscardTweetDto {
    @NotNull
    private Long tweet;

    public Long getTweet() {
        return tweet;
    }
}
