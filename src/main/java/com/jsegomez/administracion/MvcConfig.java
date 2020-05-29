package com.jsegomez.administracion;

import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        WebMvcConfigurer.super.addResourceHandlers(registry);

        String directorioFotos = Paths.get("imagenes").toAbsolutePath().toUri().toString();
        registry.addResourceHandler("/imagenes/**")
        .addResourceLocations(directorioFotos);
    }

}

















