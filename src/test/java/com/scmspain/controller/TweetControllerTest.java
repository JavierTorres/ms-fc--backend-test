package com.scmspain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scmspain.configuration.TestConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
public class TweetControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(this.context).build();
    }

    @Test
    public void shouldReturn200WhenInsertingAValidTweet() throws Exception {
        mockMvc.perform(newTweet("Prospect", "Breaking the law"))
                .andExpect(status().is(201));
    }

    @Test
    public void shouldReturn200WhenInsertingTweetWithLinkLessThan140() throws Exception {
        mockMvc.perform(newTweet("Schibsted Spain", "We are Schibsted Spain (look at our home page http://www.schibsted.es/), we own Vibbo, InfoJobs, fotocasa, coches.net and milanuncios. Welcome!"))
                .andExpect(status().is(201));
    }

    @Test
    public void shouldReturn400WhenInsertingAnInvalidTweetWithLongerTweet() throws Exception {
        mockMvc.perform(newTweet("Schibsted Spain", "We are Schibsted Spain (look at our home page http://www.schibsted.es/), we own Vibbo, InfoJobs, fotocasa, coches.net and milanuncios. Welcome! This is a test including more characters"))
                .andExpect(status().is(400));
    }

    @Test
    public void shouldReturn400WhenInsertingAnInvalidTweetNoTweet() throws Exception {
        mockMvc.perform(newTweet("Schibsted Spain", "" +
                ""))
                .andExpect(status().is(400));
    }

    @Test
    public void shouldReturn400WhenInsertingAnInvalidTweetNoPublishger() throws Exception {
        mockMvc.perform(newTweet("", "We are Schibsted Spain" +
                ""))
                .andExpect(status().is(400));
    }

    @Test
    public void shouldReturn204WhenDiscardingAValidTweet() throws Exception {
        mockMvc.perform(newTweet("Prospect", "Breaking the law"))
                .andExpect(status().is(201));

        mockMvc.perform(discardTweet(1L))
                .andExpect(status().is(204));
    }

    @Test
    public void shouldReturn204WhenDiscardingAnInvalidTweet() throws Exception {
        mockMvc.perform(discardTweet(null))
                .andExpect(status().is(400));
    }
    Breaking the law dfkmsl mfksmfdsm klsfm klmsf mskfmskld mlsfmls mkflms lfmklsmfkl smfklsmlsmflksm klsfmkl smfklsmlk smfkl mslkf mls fslmklfm

    @Test
    public void shouldReturn204WhenDiscardingANotFoundTweet() throws Exception {
        mockMvc.perform(discardTweet(999L))
                .andExpect(status().is(400));
    }

    @Test
    public void shouldReturnAllPublishedTweets() throws Exception {
        mockMvc.perform(newTweet("Yo", "How are you?"))
                .andExpect(status().is(201));

        MvcResult getResult = mockMvc.perform(get("/tweet"))
                .andExpect(status().is(200))
                .andReturn();

        String content = getResult.getResponse().getContentAsString();
        assertThat(new ObjectMapper().readValue(content, List.class).size()).isEqualTo(1);
    }

    @Test
    public void shouldReturn200WhenListingAllTweets() throws Exception {
        mockMvc.perform(get("/tweet").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is(200));
    }

    @Test
    public void shouldReturn200WhenListingDiscardedTweets() throws Exception {
        mockMvc.perform(get("/tweet/discarded").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is(200));
    }

    private MockHttpServletRequestBuilder newTweet(String publisher, String tweet) {
        return post("/tweet")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(format("{\"publisher\": \"%s\", \"tweet\": \"%s\"}", publisher, tweet));
    }

    private MockHttpServletRequestBuilder discardTweet(Long id) {
        return post("/tweet/discarded")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(format("{\"tweet\": " + id + "}"));
    }

}
