package com.example.camel.oncloud;

import org.apache.camel.CamelContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CamelContextRegistry {
    private Map<String, CamelContext> camelContextMap = new ConcurrentHashMap<>();
    public CamelContext getCamelContext(String contextID) {
        return camelContextMap.get(contextID);
    }
    public CamelContext removeCamelContext(String contextID) {
        return camelContextMap.remove(contextID);
    }
    public void putCamelContext(String contextID, CamelContext camelContext) {
        camelContextMap.put(contextID, camelContext);
    }

}
