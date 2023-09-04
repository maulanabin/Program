package com.doyatama.university.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

@Component
public class DatabaseSeeder {
    @EventListener
    public void seed(ContextRefreshedEvent event) {
//        seedRpsTable();
//        seedRpsDetailTable();
//        seedQuestionsTable();
    }
}
