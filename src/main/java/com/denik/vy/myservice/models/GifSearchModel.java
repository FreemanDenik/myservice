package com.denik.vy.myservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class GifSearchModel {
    @JsonProperty("data")
    private List<Map> model;

    public void setModel(List<Map> model) {
        this.model = model;
    }

    public List<Map> getModel() {
        return model;
    }

    public String getRandomUrl(int count) {
        Random rand = new Random();
        return ((Map) ((Map) this.model.get(rand.nextInt(count)).get("images")).get("downsized")).get("url").toString();
    }

}
