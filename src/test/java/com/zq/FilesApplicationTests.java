package com.zq;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class FilesApplicationTests {

    @Test
    void contextLoads() {

        System.out.println(DigestUtils.md5DigestAsHex("gg".getBytes()));
    }

}
