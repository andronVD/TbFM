package com.vdovich.tbfm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
@ComponentScan("com.vdovich")
public class TbFmApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(TbFmApplication.class, args);
    }

}
