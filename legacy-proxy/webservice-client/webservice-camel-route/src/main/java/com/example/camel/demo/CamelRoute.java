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

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CamelRoute extends RouteBuilder {
    // create camel endpoint for access
    String serviceURI = "cxf:http://localhost:8080/Service/Hello?serviceClass=sample.ws.service.Hello";
    String enableLoggingFeature = "loggingFeatureEnabled=true";
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
                .to(serviceURI + "&" + enableLoggingFeature)
                .log("Get the response ${body} with sayHello!")
                .bean(serviceBean, "logout")
                .log("Called the logout method!");

    }
}
