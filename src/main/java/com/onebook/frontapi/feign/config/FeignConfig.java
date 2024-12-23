package com.onebook.frontapi.feign.config;

import com.onebook.frontapi.feign.interceptor.FeignRequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public FeignRequestInterceptor feignRequestInterceptor(HttpServletRequest request){
        return new FeignRequestInterceptor(request);
    }
}
