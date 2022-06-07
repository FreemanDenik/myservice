package com.denik.vy.myservice.endpoints;

import com.denik.vy.myservice.clients.GiphyClient;
import com.denik.vy.myservice.clients.XchangeClient;
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
import java.util.stream.Collectors;

@Component
@RestControllerEndpoint(id = "compare-prices")
public class EndpointController {

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

    public EndpointController(XchangeClient xchangeClient, GiphyClient giphyClient, RestTemplate restTemplate) {
        this.xchangeClient = xchangeClient;
        this.giphyClient = giphyClient;
        this.restTemplate = restTemplate;

    }
    @GetMapping(value = "/currencies")
    public @ResponseBody String currencies() {
            return xchangeClient.currencies().getBody().entrySet().stream().map(w->w.getKey()).collect(Collectors.joining(", "));
    }
    @GetMapping(value = "/{currencyCode}", produces = MediaType.IMAGE_GIF_VALUE)
    public @ResponseBody byte[] currenciesCode(@PathVariable String currencyCode) {

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

        ResponseEntity<GifModel> responseGif = giphyClient.gifs(giphyAppId, emRich.toString(), giphyRating);
        byte[] arrByte = restTemplate.getForObject(responseGif.getBody().url(), byte[].class);

        return arrByte;
    }

}
