package com.denik.vy.myservice.models;

import lombok.Data;

import java.util.Map;
@Data
public class Info {
   private String disclaimer;
   private String license;
   private long timestamp;
   private String base;
   private Map<String, Double> rates;
}
