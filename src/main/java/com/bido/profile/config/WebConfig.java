package com.bido.profile.config;

import com.bido.profile.security.AuthContextArgumentResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Component
public class WebConfig implements WebMvcConfigurer {

    private final AuthContextArgumentResolver authContextArgumentResolver;

    public WebConfig(AuthContextArgumentResolver authContextArgumentResolver) {
        this.authContextArgumentResolver = authContextArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authContextArgumentResolver);
    }
}
