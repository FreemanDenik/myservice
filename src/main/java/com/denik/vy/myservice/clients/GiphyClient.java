package com.denik.vy.myservice.clients;

import com.denik.vy.myservice.models.GifRandomModel;
import com.denik.vy.myservice.models.GifSearchModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "gif", url = "${my.giphy.url}")
public interface GiphyClient {
    @GetMapping("/${my.giphy.random}")
    ResponseEntity<GifRandomModel> gifRandom(@RequestParam String api_key, @RequestParam String tag, @RequestParam String rating);
    @GetMapping("/${my.giphy.search}")
    ResponseEntity<GifSearchModel> gifSource(@RequestParam String api_key, @RequestParam String q, @RequestParam int limit, @RequestParam String rating);
}
