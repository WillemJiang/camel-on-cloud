/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.example.camel.demo;


import org.apache.camel.Header;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.stereotype.Component;
import sample.ws.service.Hello;

import javax.annotation.PostConstruct;
import java.util.Arrays;

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
        // Just put log feature for CXF to use
        factory.setFeatures(Arrays.asList(new LoggingFeature()));
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
