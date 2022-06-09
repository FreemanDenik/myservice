package com.denik.vy.myservice.endpoints;

import com.denik.vy.myservice.clients.GiphyClient;
import com.denik.vy.myservice.clients.XchangeClient;
import com.denik.vy.myservice.enums.EmRich;
import com.denik.vy.myservice.models.GifModel;

import com.denik.vy.myservice.models.GifRandomModel;
import com.denik.vy.myservice.models.GifSearchModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Component
@RestControllerEndpoint(id = "compare-prices")
public class CompareEndpoint {
    private final XchangeClient xchangeClient;
    private final GiphyClient giphyClient;
    private final RestTemplate restTemplate;
    // xchange
    @Value("${my.xchange.app-id}")
    private String xchangeAppId;
    @Value("${my.xchange.base-code}")
    private String xchangeBaseCode;
    // giphy
    private EmRich emRich;
    @Value("${my.giphy.app-id}")
    private String giphyAppId;
    @Value("${my.giphy.rating}")
    private String giphyRating;
    @Value("${my.giphy.limit}")
    private int giphyLimit;
    public CompareEndpoint(XchangeClient xchangeClient, GiphyClient giphyClient, RestTemplate restTemplate) {
        this.xchangeClient = xchangeClient;
        this.giphyClient = giphyClient;
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "/{currencyCode}", produces = MediaType.IMAGE_GIF_VALUE)
    public @ResponseBody ResponseEntity currenciesCode(@PathVariable String currencyCode) {

        LocalDate yesterday = LocalDate.now().minusDays(1);

        Double yesterdayPrice = xchangeClient.historical(xchangeAppId, yesterday.toString(), xchangeBaseCode).getBody().rates.entrySet().stream()
                .filter(w -> w.getKey().equals(currencyCode))
                .map(w -> w.getValue())
                .findFirst().get();
        Double latestPrice = xchangeClient.latest(xchangeAppId, xchangeBaseCode).getBody().rates.entrySet().stream()
                .filter(w -> w.getKey().equals(currencyCode))
                .map(w -> w.getValue())
                .findFirst().get();

        switch (Double.compare(latestPrice, yesterdayPrice)) {
            case -1:
                emRich = EmRich.POOR;
                break;
            case 1:
                emRich = EmRich.RICH;
                break;
            default:
                emRich = EmRich.NO_CHANGE;
        }
        //ResponseEntity<GifRandomModel> gifRandomModel = giphyClient.gifRandom(giphyAppId, emRich.toString(), giphyRating);
        ResponseEntity<GifSearchModel> gifSearchModel = giphyClient.gifSource(giphyAppId, emRich.toString(), giphyLimit, giphyRating);

        String gifUrl = gifSearchModel.getBody().getRandomUrl(giphyLimit);
        byte[] arrByte = restTemplate.getForObject(gifUrl, byte[].class);

        return new ResponseEntity<>(arrByte, HttpStatus.OK);
    }
}
