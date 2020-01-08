package com.wagnerandrade.cursomc.api.config;

import com.wagnerandrade.cursomc.api.services.DBService;
import com.wagnerandrade.cursomc.api.services.EmailService;
import com.wagnerandrade.cursomc.api.services.MockEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBService dbService;

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        this.dbService.instantiateDatabase();
        return true;
    }

    @Bean
    public EmailService emailService() {
        return new MockEmailService();
    }
}
