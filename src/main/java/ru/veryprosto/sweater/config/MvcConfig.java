package ru.veryprosto.sweater.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${upload.path}")
    private String uploadPath;

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**") //это означает что каждое обращение к серверу по пути img с последующими какими то данными
                .addResourceLocations("file:/" + uploadPath + "/"); //будет перенаправлять все запросы по этому пути
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");//это означает что ресурсы(стили) программа будет искать в дереве проекта
    }
}