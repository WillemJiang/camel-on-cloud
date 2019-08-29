package com.example.camel.demo;

import org.apache.camel.Header;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.stereotype.Component;
import sample.ws.service.Hello;

import javax.annotation.PostConstruct;

@Component
public class MyServiceBean {
    private Hello hello;

    @PostConstruct
    public void setServiceProxy() {
        // create cxf client for invocation with JAXWS API
        // HelloService service = new HelloService();
        // hello = service.getHelloPort();

        // create the cxf client by using JaxWsFactoryBean, we can do some other configuration
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setAddress("http://localhost:8080/Service/Hello");
        // as Hello.java is generated from WSDL, we don't need to specify the wsdl URL here.
        hello = factory.create(Hello.class);
    }

    public String login(@Header("username") String username, @Header("password") String password) {
        return hello.login(username, password);
    }

    public void logout(@Header("sessionId") String sessionId) {
        hello.logout(sessionId);
    }

}
