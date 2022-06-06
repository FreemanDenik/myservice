package com.denik.vy.myservice.Clients;

import com.denik.vy.myservice.Resources.InfoResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "info", url = "${my.openexchangerates.url}")
public interface InfoClient{


    @GetMapping("/latest.json")
    String latest(@RequestParam String app_id);


}
