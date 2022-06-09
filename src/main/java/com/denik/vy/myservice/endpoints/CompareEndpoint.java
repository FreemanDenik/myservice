package com.denik.vy.myservice.endpoints;

import com.denik.vy.myservice.clients.GiphyClient;
import com.denik.vy.myservice.clients.XchangeClient;
import com.denik.vy.myservice.enums.EmRich;

import com.denik.vy.myservice.models.GifSearchModel;
import com.denik.vy.myservice.models.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

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
        emRich = EmRich.NO;
    }

    @GetMapping(value = "/{currencyCode}", produces = MediaType.IMAGE_GIF_VALUE)
    public @ResponseBody byte[] currenciesCode(@PathVariable String currencyCode) {

        LocalDate yesterdayTime = LocalDate.now().minusDays(1);
        Info yesterday = Objects.requireNonNull(xchangeClient.historical(xchangeAppId, yesterdayTime.toString(), xchangeBaseCode).getBody(), "getBody return null historical method");
        Double yesterdayPrice = yesterday.rates.entrySet().stream()
                .filter(w -> w.getKey().equals(currencyCode))
                .map(Map.Entry::getValue)
                .findFirst().get();
        Info today = Objects.requireNonNull(xchangeClient.latest(xchangeAppId, xchangeBaseCode).getBody(), "getBody return null latest method");
        Double latestPrice = today.rates.entrySet().stream()
                .filter(w -> w.getKey().equals(currencyCode))
                .map(Map.Entry::getValue)
                .findFirst().get();

        switch (Double.compare(latestPrice, yesterdayPrice)) {
            case -1 -> emRich = EmRich.POOR;
            case 1 -> emRich = EmRich.RICH;
            default -> emRich = EmRich.NO;
        }
        //ResponseEntity<GifRandomModel> gifRandomModel = giphyClient.gifRandom(giphyAppId, emRich.toString(), giphyRating);
        ResponseEntity<GifSearchModel> gifSearchModel = giphyClient.gifSource(giphyAppId, emRich.toString(), giphyLimit, giphyRating);

        String gifUrl = gifSearchModel.getBody().getRandomUrl(giphyLimit);
        byte[] arrByte = restTemplate.getForObject(gifUrl, byte[].class);

        return arrByte;
    }
}