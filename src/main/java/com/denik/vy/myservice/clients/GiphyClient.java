package com.denik.vy.myservice.clients;

import com.denik.vy.myservice.models.GifSearchModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "gif", url = "${my.giphy.url}")
public interface GiphyClient {
    @GetMapping("/${my.giphy.search}")
    ResponseEntity<GifSearchModel> gifSource(@RequestParam("api_key") String id, @RequestParam String q, @RequestParam int limit, @RequestParam String rating);
}
