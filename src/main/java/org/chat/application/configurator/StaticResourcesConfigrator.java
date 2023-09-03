package org.chat.application.configurator;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class StaticResourcesConfigrator implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

/*        String currentPath = new File(".").getAbsolutePath();
        String location = "file:///" + currentPath + "/client/";
        registry.addResourceHandler("/app/**")
                .addResourceLocations(location);*/
    }

}
