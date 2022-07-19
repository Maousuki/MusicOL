package com.rjkf.music;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class MusicApplicationTests {

    @Test
    void contextLoads() {
        String s = "2022-06-13";
        LocalDateTime parse = LocalDateTime.parse(s);
        System.out.println(parse);
    }

}
