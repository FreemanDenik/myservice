package com.denik.vy.myservice.clients;

import com.denik.vy.myservice.models.GifModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "gif", url = "${my.giphy.url}")
public interface GiphyClient {
    @GetMapping
    ResponseEntity<GifModel> gifs(@RequestParam String api_key, @RequestParam String tag, @RequestParam String rating);
}
