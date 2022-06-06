package com.denik.vy.myservice.clients;

import com.denik.vy.myservice.models.Info;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@FeignClient(value = "info", url = "${my.openexchangerates.url}")
public interface InfoClient{
    @GetMapping("/${my.openexchangerates.currencies}")
    ResponseEntity<Info> currencies();
    @GetMapping("/${my.openexchangerates.latest}")
    ResponseEntity<Info> latest(@RequestParam String app_id);

    @GetMapping("/${my.openexchangerates.historical}/{date}.json")
    ResponseEntity<Info> historical(@RequestParam String app_id, @PathVariable String date);


}
