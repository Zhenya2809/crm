package com.evgeniy.config;

import com.evgeniy.filters.RequestResponseLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {


    @Bean
    public FilterRegistrationBean<RequestResponseLoggingFilter> loggingFilter() {
        FilterRegistrationBean<RequestResponseLoggingFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new RequestResponseLoggingFilter());

        registrationBean.addUrlPatterns("/registration/*");
        registrationBean.addUrlPatterns("/profile/*");
        registrationBean.addUrlPatterns("/admin/*");
        registrationBean.addUrlPatterns("/profileEdit/*");
        registrationBean.addUrlPatterns("/error/*");
        registrationBean.addUrlPatterns("/doctor/*");
        registrationBean.addUrlPatterns("/patient/*");
        registrationBean.addUrlPatterns("/clinic/*");
        registrationBean.addUrlPatterns("/user/*");

        registrationBean.setOrder(1);

        return registrationBean;

    }

}