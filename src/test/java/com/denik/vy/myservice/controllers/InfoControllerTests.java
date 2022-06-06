package com.denik.vy.myservice.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.io.IOException;

@SpringBootTest
public class InfoControllerTests {
    @Autowired
    InfoController infoController;
    @Test
    void test1() throws IOException {
        byte[] s = infoController.getImageWithMediaType();

    }
}
