package com.example.camel.demo;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CamelRoute extends RouteBuilder {
    // create camel endpoint for access
    String serviceURI = "cxf:http://localhost:8080/Service/Hello?serviceClass=sample.ws.service.Hello";
    @Autowired
    MyServiceBean serviceBean;

    @Override
    public void configure() throws Exception {
        from("timer://foo?fixedRate=true&period=60000")
                // setup the username and password
                .process(ex -> {
                    ex.getMessage().setHeader("username", "user1");
                    ex.getMessage().setHeader("password", "pass");
                })
                // Get Session ID
                .bean(serviceBean, "login")
                // Write the session Id down
                .log("Get the session id ${body}")
                // Prepare the parameters
                .process(ex -> {
                    String sessionId = ex.getMessage().getBody(String.class);
                    Object[] parameters = new Object[2];
                    parameters[0] = sessionId;
                    parameters[1] = "Willem";
                    // prepare the invocation parameters
                    ex.getMessage().setBody(parameters);
                    // Setup the operationInfo
                    ex.getMessage().setHeader(CxfConstants.OPERATION_NAME, "sayHello");
                    // put the sessionId to message header
                    ex.getMessage().setHeader("sessionId", sessionId);
                })
                .to(serviceURI)
                .log("Get the response ${body} with sayHello!")
                .bean(serviceBean, "logout")
                .log("Called the logout method!");

    }
}
