package com.example.camel.oncloud;

import org.apache.camel.component.servlet.HttpRegistry;
import org.apache.camel.http.common.CamelServlet;
import org.apache.camel.http.common.HttpConsumer;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

// This registry can only store one camelServlet
@Component
public class MyHttpRegistry implements HttpRegistry {
    private Set<HttpConsumer> httpConsumers = new HashSet<>();
    private CamelServlet camelServlet;
    @Override
    public void register(HttpConsumer consumer) {
        httpConsumers.add(consumer);
        // Current camel servlet just override the consumer with same endpoint URI
        camelServlet.connect(consumer);
    }

    @Override
    public void unregister(HttpConsumer consumer) {
        httpConsumers.remove(consumer);
        // To avoid removing the override consumer
        if (camelServlet.getConsumers().containsValue(consumer)) {
            camelServlet.disconnect(consumer);
        }
    }

    @Override
    public void register(CamelServlet provider) {
        camelServlet = provider;
    }

    @Override
    public void unregister(CamelServlet provider) {
        if (camelServlet.equals(provider)) {
            camelServlet = null;
        }

    }

    @Override
    public CamelServlet getCamelServlet(String servletName) {
        // just return the camelServlet regardless with servletName
        return camelServlet;
    }
}
