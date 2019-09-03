package com.example.camel.oncloud;

import org.apache.camel.CamelContext;
import org.apache.camel.component.servlet.DefaultHttpRegistry;
import org.apache.camel.component.servlet.HttpRegistry;
import org.apache.camel.component.servlet.ServletComponent;
import org.apache.camel.impl.CompositeRegistry;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.net.URLDecoder;


@Controller
public class ServiceController {
    @Autowired
    CamelContextRegistry camelContextRegistry;
    @Autowired
    private CamelContext parentCamelContext;
    @Autowired
    private HttpRegistry httpRegistry;
    private CamelContext camelContext;


    SimpleRegistry simpleRegistry = new SimpleRegistry();

    @RequestMapping("/camel/start")
    @ResponseBody
    public String startNewApplication() throws Exception {
        // Create a new camel context and setup the HttpRegistry to look up the servlet
        camelContext = new DefaultCamelContext();
        ServletComponent servletComponent = new ServletComponent();
        servletComponent.setHttpRegistry(httpRegistry);
        camelContext.addComponent("servlet", servletComponent);
        // Bridge the new created camelContext with the parentCamelContext
        enhanceCamelContext(camelContext);
        camelContext.addRoutes(new MySpringBootRouter());
        camelContext.start();
        return "Done";
    }

    @RequestMapping("/camel/stop")
    @ResponseBody
    public String createNewApplication() throws Exception {
        camelContext.stop();
        return "Done";
    }

    public void enhanceCamelContext(CamelContext camelContext) {
        // Share the registry of parentCamelContext
        CompositeRegistry compositeRegistry = getCompositeRegistry();
        compositeRegistry.addRegistry(parentCamelContext.getRegistry());
        compositeRegistry.addRegistry(camelContext.getRegistry());
        // Need to update the registry like this
        ((DefaultCamelContext)camelContext).setRegistry(compositeRegistry);

    }

    private CompositeRegistry getCompositeRegistry() {
        CompositeRegistry registry = new CompositeRegistry();
        return registry;
    }

}
