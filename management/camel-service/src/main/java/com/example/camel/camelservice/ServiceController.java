package com.example.camel.camelservice;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLDecoder;


@Controller
public class ServiceController {

    @Autowired
    CamelContext camelContext;

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

}
