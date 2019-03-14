package com.example.camel.camelservice;

import org.apache.camel.CamelContext;
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
    CamelContext camelContext;

    SimpleRegistry simpleRegistry = new SimpleRegistry();

    @RequestMapping("/camel/start")
    @ResponseBody
    public String startCamelRoute() throws Exception {
        camelContext.startRoute("route1");

        return "Done";
    }

    @RequestMapping("/camel/stop")
    @ResponseBody
    public String stopCamelRoute() throws Exception {
        camelContext.stopRoute("route1");
        return "Done";
    }

    @RequestMapping("/camel/routes")
    @ResponseBody
    public String getRoutes() throws Exception {
        return camelContext.getManagedCamelContext().dumpRoutesAsXml(true);
    }

    @RequestMapping(path="/camel/routes", method= RequestMethod.POST)
    @ResponseBody
    public String setRoutes(@RequestBody String body) throws Exception {
        System.out.println(body);
        // Need to decode the code first
        String camelRoute = URLDecoder.decode(body, "UTF-8");
        camelContext.getManagedCamelContext().addOrUpdateRoutesFromXml(camelRoute);
        return "OK";
    }

    @PostConstruct
    public void enhanceCamelContext() {
        // replace the registry
        CompositeRegistry compositeRegistry = getCompositeRegistry();
        compositeRegistry.addRegistry(camelContext.getRegistry());
        compositeRegistry.addRegistry(simpleRegistry);
        // Need to update the registry like this
        ((DefaultCamelContext)camelContext).setRegistry(compositeRegistry);
        simpleRegistry.put("myBean", new Bar());

    }

    public class Bar {
        public String doSomething(String body) {
            // process the in body and return whatever you want
            return "Bye World";
        }
    }

    private CompositeRegistry getCompositeRegistry() {
        CompositeRegistry registry = new CompositeRegistry();
        return registry;
    }

}
