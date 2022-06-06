package com.denik.vy.myservice.Resources;

import com.denik.vy.myservice.models.Info;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


public interface InfoResource {

    ResponseEntity<Info> getInfo();
}
