package com.denik.vy.myservice.clients;

import com.denik.vy.myservice.models.Info;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@FeignClient(value = "info", url = "${my.xchange.url}")
public interface XchangeClient {
    @GetMapping("/${my.xchange.currencies}")
    ResponseEntity<Map<String, String>> currencies();
    @GetMapping("/${my.xchange.latest}")
    ResponseEntity<Info> latest(@RequestParam String app_id, @RequestParam("base") String currency);

    @GetMapping("/${my.xchange.historical}/{date}.json")
    ResponseEntity<Info> historical(@RequestParam String app_id, @PathVariable String date, @RequestParam("base") String currency);


}
