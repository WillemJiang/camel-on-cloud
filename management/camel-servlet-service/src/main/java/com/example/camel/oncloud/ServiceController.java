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
    private int count;


    SimpleRegistry simpleRegistry = new SimpleRegistry();

    @RequestMapping("/camel/start")
    @ResponseBody
    public String startNewApplication() throws Exception {
        CamelContext oldCamelContext = camelContext;
        // Create a new camel context and setup the HttpRegistry to look up the servlet
        camelContext = new DefaultCamelContext();
        // Create a new servlet component per camel context
        ServletComponent servletComponent = new ServletComponent();
        servletComponent.setHttpRegistry(httpRegistry);
        camelContext.addComponent("servlet", servletComponent);
        // Bridge the new created camelContext with the parentCamelContext
        enhanceCamelContext(camelContext);
        camelContext.addRoutes(new MySpringBootRouter(new Integer(count++).toString()));
        camelContext.start();
        if (oldCamelContext != null) {
            oldCamelContext.stop();
        }
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
