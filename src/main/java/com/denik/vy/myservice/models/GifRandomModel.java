package com.denik.vy.myservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Data
public class GifRandomModel {
    @JsonProperty("data")
    private Map model;
    public String getUrl(int count){
        Random rand = new Random();
        return ((Map)((Map)this.model.get("images")).get("downsized")).get("url").toString();
    }

}
