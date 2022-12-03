package com.ssk.ng.clickhouseclient;

import com.ssk.ng.clickhouseclient.config.WebRootConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.util.HashMap;
import java.util.Map;

/**
 * Produces mock datacd
 */

@SpringBootApplication
@Import({WebRootConfig.class})
public class ClickhouseClientApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ClickhouseClientApplication.class);
        Map<String, Object> map = new HashMap<>();
        map.put("server.servlet.contextPath", "/ch-client");
        map.put("server.port", "8484");
        application.setDefaultProperties(map);
        application.run(args);
    }

}
