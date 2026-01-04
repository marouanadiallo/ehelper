package com.dialltay.ehelper;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class EhelperApplication {

    public static void main(final String[] args) {
        var ehelperApplication = new SpringApplication(EhelperApplication.class);
        ehelperApplication.setBannerMode(Banner.Mode.OFF);
        ehelperApplication.run(args);
    }

}
