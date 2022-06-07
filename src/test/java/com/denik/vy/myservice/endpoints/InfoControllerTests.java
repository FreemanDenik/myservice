package com.denik.vy.myservice.endpoints;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class InfoControllerTests {

    @Test
    void test1() throws IOException {
        double ddd = 90.11112;
        double fff = 90.11111;

        int yty = Double.compare(ddd,fff);
        int yty1 = Double.compare(fff,ddd);
        Double ss =  Double.max(ddd,fff);

    }
}
