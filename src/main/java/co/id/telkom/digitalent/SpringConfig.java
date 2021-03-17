package co.id.telkom.digitalent;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        // Public Images in Current Path Uploads Directory
        String[] resources = {"file:uploads//"};
        registry.addResourceHandler("/images/**").addResourceLocations(resources);
    }
}
