package com.example.projectmanagementbe.auth.configs.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class CorsConfig implements WebMvcConfigurer {

  @Value("${web-config.cors.allowed.origins}")
  private String allowedOrigins;

  @Value("${web-config.cors.allowed.methods}")
  private String[] allowedMethods;

  private final StorageConfig storageConfig;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins(allowedOrigins)
            .allowedMethods(allowedMethods)
            .allowedHeaders("*")
            .allowCredentials(true);
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/storage/**")
            .addResourceLocations("file:" + storageConfig.getDirectory() + "/");
  }
}