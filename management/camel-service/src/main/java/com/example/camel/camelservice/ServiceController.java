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

    @Autowired
    CamelConsumerBridge camelConsumerBridge;

    SimpleRegistry simpleRegistry = new SimpleRegistry();

    @RequestMapping("camel/consumer")
    @ResponseBody
    public String consumerMessage() throws Exception {
        camelConsumerBridge.consumerMessage();
        return "Done";
    }

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
