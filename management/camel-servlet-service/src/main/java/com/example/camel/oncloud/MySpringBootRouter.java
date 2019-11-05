package com.example.camel.oncloud;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */

// This route should be create by ServiceController

public class MySpringBootRouter extends RouteBuilder {

    private String perfix;

    public MySpringBootRouter(String prefix) {
        this.perfix = prefix;
    }

    @Override
    public void configure() {
        restConfiguration().component("servlet");
        rest("/test").get("/{id}").route()
            .setHeader("prefix").constant(perfix)
            .transform().method("myBean", "saySomething")
            .transform().simple("${header.prefix} ${header.id} ${body}")
            .end();

    }

}
