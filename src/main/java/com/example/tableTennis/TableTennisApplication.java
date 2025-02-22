package com.example.tableTennis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TableTennisApplication {

    public static void main(String[] args) {
        SpringApplication.run(TableTennisApplication.class, args);
    }

}
