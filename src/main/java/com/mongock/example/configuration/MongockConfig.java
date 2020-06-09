package com.mongock.example.configuration;

import com.github.cloudyrock.mongock.SpringMongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@RequiredArgsConstructor
public class MongockConfig {

    private final MongoTemplate mongoTemplate;

    @Bean
    public SpringMongock mongock() {
        return new SpringMongockBuilder(mongoTemplate, "com.mongock.example.changeset")
                .setLockQuickConfig()
                .build();
    }
}
