package com.webtech.schoolfeedingsystemtracker.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class ThymeleafConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages"); // Ensure this path is correct
        messageSource.setDefaultEncoding("UTF-8"); // Ensure encoding is UTF-8 for special characters
        return messageSource;
    }
}
