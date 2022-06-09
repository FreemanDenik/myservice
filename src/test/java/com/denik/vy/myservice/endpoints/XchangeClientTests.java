package com.denik.vy.myservice.endpoints;

import com.denik.vy.myservice.clients.XchangeClient;
import com.denik.vy.myservice.models.Info;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class XchangeClientTests {
    @MockBean
    private XchangeClient xchangeClient;
    // xchange
    @Value("${my.xchange.app-id}")
    private String xchangeAppId;
    @Value("${my.xchange.base-code}")
    private String xchangeBaseCode;


    @Test
    void currenciesTest() {
        Map<String, String> result = new HashMap<>();
        result.put("AED","United Arab Emirates Dirham");
        result.put("...","...");
        result.put("ZWL","Zimbabwean Dollar");
        ResponseEntity<Map<String, String>> responseReturn = new ResponseEntity<>(result, HttpStatus.OK);

        Mockito.when(xchangeClient.currencies()).thenReturn(responseReturn);

        ResponseEntity<Map<String, String>> response = xchangeClient.currencies();

        Assert.isTrue(HttpStatus.OK == response.getStatusCode(), "invalid currencies post");

        Long count = response.getBody().entrySet().stream().count();
        Map.Entry<String, String> AED = response.getBody().entrySet().stream().findFirst().get();
        Map.Entry<String, String> ZWL = response.getBody().entrySet().stream().skip(count-1).findFirst().get();

        Assert.notNull(response, "response is null!");

        Assert.isTrue(AED.getKey().equals("AED"), "not compare AED key");
        Assert.isTrue(AED.getValue().equals("United Arab Emirates Dirham"), "not compare AED value");

        Assert.isTrue(ZWL.getKey().equals("ZWL"), "not compare ZWL key");
        Assert.isTrue(ZWL.getValue().equals("Zimbabwean Dollar"), "not compare ZWL value");

        Mockito.verify(xchangeClient, Mockito.atMost(1)).currencies();
    }
    @Test
    public void latestTest(){
        Info info = new Info();

        Map<String, Double> result = new HashMap<>();
        result.put("AED",1D);
        result.put("...",0.1D);
        result.put("ZWL",1D);
        info.rates = result;
        ResponseEntity<Info> responseReturn = new ResponseEntity<>(info, HttpStatus.OK);

        Mockito.when(xchangeClient.latest(xchangeAppId, xchangeBaseCode)).thenReturn(responseReturn);

        ResponseEntity<Info> response = xchangeClient.latest(xchangeAppId, xchangeBaseCode);

        Assert.isTrue(HttpStatus.OK == response.getStatusCode(), "invalid latest post");

        Long count = response.getBody().rates.entrySet().stream().count();
        Map.Entry<String, Double> AED = response.getBody().rates.entrySet().stream().findFirst().get();
        Map.Entry<String, Double> ZWL = response.getBody().rates.entrySet().stream().skip(count-1).findFirst().get();

        Assert.isTrue(AED.getKey().equals("AED"), "not compare AED key");
        Assert.isTrue(AED.getValue() != 0, "null AED value");

        Assert.isTrue(ZWL.getKey().equals("ZWL"), "not compare ZWL key");
        Assert.isTrue(ZWL.getValue() != 0, "null ZWL value");

        Mockito.verify(xchangeClient, Mockito.atMost(1)).latest(xchangeAppId,  xchangeBaseCode);
    }
    @Test
    public void historicalTest(){
        LocalDate yesterday = LocalDate.now().minusDays(1);
        Info info = new Info();

        Map<String, Double> result = new HashMap<>();
        result.put("AED",1D);
        result.put("...",0.1D);
        result.put("ZWL",1D);
        info.rates = result;
        ResponseEntity<Info> responseReturn = new ResponseEntity<>(info, HttpStatus.OK);

        Mockito.when(xchangeClient.historical(xchangeAppId, yesterday.toString(), xchangeBaseCode)).thenReturn(responseReturn);

        ResponseEntity<Info> response = xchangeClient.historical(xchangeAppId, yesterday.toString(), xchangeBaseCode);

        Assert.isTrue(HttpStatus.OK == response.getStatusCode(), "invalid historical post");

        Long count = response.getBody().rates.entrySet().stream().count();
        Map.Entry<String, Double> AED = response.getBody().rates.entrySet().stream().findFirst().get();
        Map.Entry<String, Double> ZWL = response.getBody().rates.entrySet().stream().skip(count-1).findFirst().get();


        Assert.isTrue(AED.getKey().equals("AED"), "not compare AED key");
        Assert.isTrue(AED.getValue() != 0, "null AED value");

        Assert.isTrue(ZWL.getKey().equals("ZWL"), "not compare ZWL key");
        Assert.isTrue(ZWL.getValue() != 0, "null ZWL value");

        Mockito.verify(xchangeClient, Mockito.atMost(1)).historical(xchangeAppId, yesterday.toString(), xchangeBaseCode);
    }
}
