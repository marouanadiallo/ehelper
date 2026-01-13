package com.dialltay.ehelper;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;


@SpringBootApplication
public class EhelperApplication {

    public static void main(final String[] args) {
        var ehelperApplication = new SpringApplication(EhelperApplication.class);
        ehelperApplication.setBannerMode(Banner.Mode.OFF);
        ehelperApplication.setWebApplicationType(WebApplicationType.SERVLET);
        //ehelperApplication.setDefaultProperties(Map.of("spring.config.on-not-found", "ignore"));
        ehelperApplication.run(args);
    }

}
