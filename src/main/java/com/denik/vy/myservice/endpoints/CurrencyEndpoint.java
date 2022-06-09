package com.denik.vy.myservice.endpoints;

import com.denik.vy.myservice.clients.XchangeClient;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;
@Component
@RestControllerEndpoint(id = "currencies")
public class CurrencyEndpoint {
    private final XchangeClient xchangeClient;

    public CurrencyEndpoint(XchangeClient xchangeClient) {
        this.xchangeClient = xchangeClient;
    }

    @GetMapping
    public @ResponseBody String currencies() {
        return xchangeClient.currencies().getBody().entrySet().stream().map(w->w.getKey()).collect(Collectors.joining(", "));
    }
}
