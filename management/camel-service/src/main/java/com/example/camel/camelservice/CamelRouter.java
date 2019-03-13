package com.example.camel.camelservice;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class CamelRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer:hello?period={{timer.period}}")
                .routeId("route1")
                .setBody().constant("Hello Camel!")
                .to("stream:out");
    }
}
