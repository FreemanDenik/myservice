package com.denik.vy.myservice.endpoints;

import com.denik.vy.myservice.clients.GifClient;
import com.denik.vy.myservice.clients.InfoClient;
import com.denik.vy.myservice.enums.EmRich;
import com.denik.vy.myservice.models.GifModel;

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
public class EndpointController {

    private final InfoClient infoClient;
    private final GifClient gifClient;
    private final RestTemplate restTemplate;
    // Currency
    @Value("${my.openexchangerates.app-id}")
    private String currencyApp_id;
    @Value("${my.openexchangerates.compare-currency-code}")
    private String compareCurrencyCode;
    // GIF
    private EmRich emRich;
    @Value("${my.developers.app-id}")
    private String gifApp_id;
    @Value("${my.developers.rating}")
    private String gifRating;

    public EndpointController(InfoClient infoClient, GifClient gifClient, RestTemplate restTemplate) {
        this.infoClient = infoClient;
        this.gifClient = gifClient;
        this.restTemplate = restTemplate;

    }

    @GetMapping(value = "/{currencyCode}", produces = MediaType.IMAGE_GIF_VALUE)
    public @ResponseBody byte[] customEndPoint(@PathVariable String currencyCode) {

        LocalDate yesterday = LocalDate.now().minusDays(1);

        Double yesterdayPrices = infoClient.historical(currencyApp_id, yesterday.toString(), compareCurrencyCode).getBody().rates.entrySet().stream()
                .filter(w -> w.getKey().equals(currencyCode))
                .map(w -> w.getValue())
                .findFirst().get();
        Double latestPrices = infoClient.latest(currencyApp_id, compareCurrencyCode).getBody().rates.entrySet().stream()
                .filter(w -> w.getKey().equals(currencyCode))
                .map(w -> w.getValue())
                .findFirst().get();

        switch (Double.compare(latestPrices, yesterdayPrices)) {
            case -1:
                emRich = EmRich.POOR;
                break;
            case 1:
                emRich = EmRich.RICH;
                break;
            default:
                emRich = EmRich.WITHOUT;
        }

        ResponseEntity<GifModel> responseGif = gifClient.gifs(gifApp_id, emRich.toString(), gifRating);
        byte[] arrByte = restTemplate.getForObject(responseGif.getBody().url(), byte[].class);

        return arrByte;
    }

}
