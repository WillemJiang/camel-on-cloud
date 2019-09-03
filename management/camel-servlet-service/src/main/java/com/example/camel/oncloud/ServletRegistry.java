package com.example.camel.oncloud;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.util.ImmediateInstanceHandle;
import org.apache.camel.CamelContext;
import org.apache.camel.Service;
import org.apache.camel.component.servlet.HttpRegistry;
import org.apache.camel.http.common.CamelServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServlet;

@Component
// Need to create the servlet deployment ourselves
public class ServletRegistry implements Service {
    private final static Logger LOGGER = LoggerFactory.getLogger(ServletRegistry.class);
    private CamelServlet camelServlet = new CamelServlet();
    @Autowired
    private HttpRegistry httpRegistry;
    @Autowired
    private CamelContext camelContext;

    private Undertow server;
    private DeploymentManager manager;
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
    }

    @Override
    public void start() throws Exception {
        // start the server
        DeploymentInfo servletBuilder = Servlets.deployment()
                .setClassLoader(ServletRegistry.class.getClassLoader())
                .setContextPath(contextPath)
                .setDeploymentName("undertow_server");
        servletBuilder.addServlet(
                Servlets.servlet("CamelServlet", HttpServlet.class, () -> new ImmediateInstanceHandle(camelServlet)).addMapping("/*"));

        manager = Servlets.defaultContainer().addDeployment(servletBuilder);
        manager.deploy();

        PathHandler path = Handlers.path(Handlers.redirect(contextPath)).addPrefixPath(contextPath, manager.start());
        server = Undertow.builder().addHttpListener(port, host).setHandler(path).build();
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
