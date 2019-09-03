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

    @Override
    public void configure() {
        from("servlet:/test").routeId("servlet")
            .transform().method("myBean", "saySomething")
            .end();

        from("timer:test?period=2000").routeId("timer")
                .transform().method("myBean", "saySomething")
                .to("log:foo")
                .end();

    }

}
