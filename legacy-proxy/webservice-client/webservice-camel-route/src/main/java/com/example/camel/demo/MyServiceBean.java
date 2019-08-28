package com.example.camel.demo;

import org.apache.camel.Header;
import org.springframework.stereotype.Component;
import sample.ws.service.Hello;
import sample.ws.service.HelloService;

import javax.annotation.PostConstruct;

@Component
public class MyServiceBean {
    private Hello hello;

    @PostConstruct
    public void setServiceProxy() {
        // create cxf client for invocation
        HelloService service = new HelloService();
        hello = service.getHelloPort();
    }

    public String login(@Header("username") String username, @Header("password") String password) {
        return hello.login(username, password);
    }

    public void logout(@Header("sessionId") String sessionId) {
        hello.logout(sessionId);
    }

}
