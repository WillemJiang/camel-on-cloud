package com.example.camel.oncloud;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.component.servlet.HttpRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ServletRegistry {

    private CamelHttpTransportServlet camelHttpTransportServlet = new CamelHttpTransportServlet();

    @Autowired
    private HttpRegistry httpRegistry;

    @Bean
    // Just registry the CamelServlet
    ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servlet = new ServletRegistrationBean (camelHttpTransportServlet, "/service/*");
        servlet.setName("CamelServlet");
        return servlet;
    }

    @PostConstruct
    // Register the CamelServlet into HttpRegistry
    void registerToHttpRegistry() {
        httpRegistry.register(camelHttpTransportServlet);
    }

}
