package com.denik.vy.myservice.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;


public class GifModel {
    @JsonProperty("data")
    public Data data;
    public String url(){
       return this.data.image.gif.url;
    };

    //public Map meta;
}
 class Data {
    @JsonProperty("images")
    public Images image;
}
 class Images {
    @JsonProperty("downsized_large")
    public Gif gif;
}
 class Gif {
    @JsonProperty("url")
    public String url;
}
