package com.denik.vy.myservice.endpoints;

import com.denik.vy.myservice.clients.GiphyClient;
import com.denik.vy.myservice.enums.EmRich;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class GiphyClientTests {
    @MockBean
    private GiphyClient giphyClient;
    @MockBean
    private RestTemplate restTemplate;
    // giphy
    private EmRich emRich = EmRich.RICH;
    @Value("${my.giphy.app-id}")
    private String giphyAppId;
    @Value("${my.giphy.rating}")
    private String giphyRating;
    @Test
    public void gifs(){
//        String url = "http://image.gif";
//        Gif gifModel = new GifModel() {
//            @Override
//            public String url() {
//                return url;
//            }
//        };
//
//        ResponseEntity<GifModel> responseReturn = new ResponseEntity<>(gifModel, HttpStatus.OK);
//
//        Mockito.when(giphyClient.gifRandom(giphyAppId, emRich.toString(), giphyRating)).thenReturn(responseReturn);
//
//        ResponseEntity<GifModel> response = giphyClient.gifRandom(giphyAppId, emRich.toString(), giphyRating);
//
//        Assert.isTrue(response.getBody().url().equals(url), "not valid url");
//
//        Mockito.verify(giphyClient, Mockito.atMost(1)).gifRandom(giphyAppId, emRich.toString(), giphyRating);
    }

}
