package com.denik.vy.myservice.endpoints;

import com.denik.vy.myservice.clients.XchangeClient;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
@Endpoint(id = "currencies")
public class CurrencyEndpoint {
    private final XchangeClient xchangeClient;

    public CurrencyEndpoint(XchangeClient xchangeClient) {
        this.xchangeClient = xchangeClient;
    }
    // Получаем список всех кодов допустимых к сравнению валют (USD,BTC и т.д.)
    @ReadOperation
    public String[] currencies() {
        Map<String, String> currencies = Objects.requireNonNull(xchangeClient.currencies().getBody(), "getBody return null currencies method");
        return currencies.keySet().toArray(String[]::new);
    }
}
