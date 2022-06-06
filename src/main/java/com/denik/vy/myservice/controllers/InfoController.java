package com.denik.vy.myservice.controllers;

import com.denik.vy.myservice.clients.InfoClient;
import com.denik.vy.myservice.models.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;

@RestController
public class InfoController {

    private final InfoClient infoClient;
    private final RestTemplate restTemplate;
    @Value("${my.openexchangerates.app-id}")
    private String app_id;
    @Value("${my.openexchangerates.currency-code}")
    private String currencyCode;

    public InfoController(InfoClient infoClient, RestTemplate restTemplate) {
        this.infoClient = infoClient;
        this.restTemplate = restTemplate;
    }
    @GetMapping("/")
    @ResponseBody
    public String index(@RequestParam String currency){
        LocalDate yesterday = LocalDate.now().minusDays(1);

        Info yesterdayPrices = infoClient.historical(app_id, yesterday.toString()).getBody();//.entrySet().stream().filter(w -> w.getKey().equals("USD")).findFirst().get().getValue();
        Info latestPrices = infoClient.latest(app_id).getBody();

        return "";
    }
    @GetMapping(value = "/get-image-with-media-type", produces = MediaType.IMAGE_GIF_VALUE)
    public @ResponseBody byte[] getImageWithMediaType() throws IOException {

        byte[] response = restTemplate.getForObject("https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/5eeea355389655.59822ff824b72.gif", byte[].class);


        return response;
    }
    @GetMapping(value = "/current-price")
    @ResponseBody
    public ResponseEntity<Info> getInfo() {


        return infoClient.latest(app_id);
    }

}
