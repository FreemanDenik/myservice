package com.denik.vy.myservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

public class Info {
   public String disclaimer;
   public String license;
   public long timestamp;
   public String base;
   public Map<String, Double> rates;
}
