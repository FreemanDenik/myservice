package com.denik.vy.myservice.controllers;

import com.denik.vy.myservice.Clients.InfoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    private final InfoClient infoClient;
    @Value("${my.openexchangerates.app_id}")
    String app_id;

    public InfoController(InfoClient infoClient) {
        this.infoClient = infoClient;
    }


    @GetMapping("/current-price")
    public String getInfo() {

        return infoClient.latest(app_id);
    }

}
