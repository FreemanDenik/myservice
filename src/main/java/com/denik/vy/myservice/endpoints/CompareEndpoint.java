package com.denik.vy.myservice.endpoints;

import com.denik.vy.myservice.clients.GiphyClient;
import com.denik.vy.myservice.clients.XchangeClient;
import com.denik.vy.myservice.enums.EmRich;
import com.denik.vy.myservice.models.GifSearchModel;
import com.denik.vy.myservice.models.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@Component
@Endpoint(id = "compare")
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
        this.emRich = EmRich.NO;
    }
    // Отправляем код валюты и получаем GIF в соответствии, rich, broke
    @ReadOperation(produces = MediaType.IMAGE_GIF_VALUE)
    public byte[] compare(@Selector String currencyCode) {

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
        ResponseEntity<GifSearchModel> gifSearchModel = Objects.requireNonNull(giphyClient.gifSource(giphyAppId, emRich.toString(), giphyLimit, giphyRating), "getBody return null gifSource method");

        String gifUrl = gifSearchModel.getBody().getRandomUrl(giphyLimit);

        return restTemplate.getForObject(gifUrl, byte[].class);
    }
}