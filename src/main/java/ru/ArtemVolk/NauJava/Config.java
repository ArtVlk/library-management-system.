package ru.ArtemVolk.NauJava;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import java.util.List;
import java.util.ArrayList;
import models.User;
import models.Book;

@Configuration
public class Config {
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public List<User> userContainer(){
        return new ArrayList<>();
    }

    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public List<Book> bookContainer() {
        return new ArrayList<>();
    }
}
