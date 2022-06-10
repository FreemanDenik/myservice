package com.denik.vy.myservice.endpoints;

import com.denik.vy.myservice.clients.GiphyClient;
import com.denik.vy.myservice.enums.EmRich;
import com.denik.vy.myservice.models.GifSearchModel;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.util.*;

@SpringBootTest
public class GiphyClientTests {
    @MockBean
    private GiphyClient giphyClient;
    // giphy
    private final EmRich emRich = EmRich.RICH;
    @Value("${my.giphy.app-id}")
    private String giphyAppId;
    @Value("${my.giphy.rating}")
    private String giphyRating;
    @Value("${my.giphy.limit}")
    private int giphyLimit;

    @Test
    public void gifs() {
        String url = "http://image.gif";
        GifSearchModel searchModel = new GifSearchModel();

        Map map = new HashMap<>();
        map.put("images", String.format("{'downsized':{'url':'%s'}}", url));
        List<Map> model = Arrays.asList(map);
        searchModel.setModel(model);
        ResponseEntity<GifSearchModel> responseReturn = new ResponseEntity<>(searchModel, HttpStatus.OK);

        Mockito.when(giphyClient.gifSource(giphyAppId, emRich.toString(), giphyLimit, giphyRating)).thenReturn(responseReturn);

        ResponseEntity<GifSearchModel> response = giphyClient.gifSource(giphyAppId, emRich.toString(), giphyLimit, giphyRating);

        Gson json = new Gson();

        Map data = json.fromJson(response.getBody().getModel().get(0).toString(), Map.class);
        Map images = (Map) data.get("images");
        Map downsized = (Map) images.get("downsized");
        String gifUrl = downsized.get("url").toString();

        Assert.isTrue(gifUrl.equals(url), "not valid url");

        Mockito.verify(giphyClient, Mockito.atMost(1)).gifSource(giphyAppId, emRich.toString(), giphyLimit, giphyRating);
    }

}
