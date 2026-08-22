package com.tokio.filme.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    //função para dizer ao spring para servir as imagens nas pastas de uploads
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(
                        "file:uploads/",
                        "file:src/main/java/com/tokio/filme/uploads/"
                );
    }
}
