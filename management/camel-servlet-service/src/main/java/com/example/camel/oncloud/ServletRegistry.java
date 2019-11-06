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

package com.example.camel.oncloud;

import org.apache.camel.CamelContext;
import org.apache.camel.Service;
import org.apache.camel.component.servlet.HttpRegistry;
import org.apache.camel.http.common.CamelServlet;
import org.apache.camel.http.common.HttpRestServletResolveConsumerStrategy;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
// Need to create the servlet deployment ourselves
public class ServletRegistry implements Service {
    private final static Logger LOGGER = LoggerFactory.getLogger(ServletRegistry.class);
    private CamelServlet camelServlet = new CamelServlet();
    @Autowired
    private HttpRegistry httpRegistry;
    @Autowired
    private CamelContext camelContext;

    private Server  server;
    @Value("${servlet.context.path:/service}")
    private String contextPath;
    @Value("${servlet.host:localhost}")
    private String host;
    @Value("${servlet.port:9000}")
    private int port;


    @PostConstruct
    // Register the CamelServlet into HttpRegistry
    void registerToHttpRegistry() throws Exception {
        // Just start the service
        httpRegistry.register(camelServlet);
        camelContext.addService(this, true);
        // Using the rest servlet resolve to lookup the consumers
        camelServlet.setServletResolveConsumerStrategy(new HttpRestServletResolveConsumerStrategy());
    }

    @Override
    public void start() throws Exception {
        // start the jetty server with servlet support
        server = new Server(port);
        ServletHandler handler = new ServletHandler();
        server.addBean(new CustomerErrorHandler());
        // setup the error handler of the server
        server.setHandler(handler);
        handler.addServletWithMapping(new ServletHolder(camelServlet), contextPath + "/*");
        server.start();
        LOGGER.info("Starting servlet engine on {}:{}{}", host, port, contextPath);
    }

    @Override
    public void stop() throws Exception {
        if (this.server != null) {
            LOGGER.info("Stopping servlet engine");
            this.server.stop();
        }
    }
}
