package com.example.camel.camelservice;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CamelConsumerBridge {
    @Autowired
    CamelContext camelContext;
    ConsumerTemplate consumer;
    ProducerTemplate producer;

    // We could do some configuration here

    public void consumerMessage() throws Exception {
        consumer = camelContext.createConsumerTemplate();
        producer = camelContext.createProducerTemplate();

        // Start the consumer and producer service
        consumer.start();
        producer.start();

        //TODO we may need to setup the timeout value for it
        // Now we can count the message size
        Exchange exchange = consumer.receive("seda:message");

        // Now we just send message out to another route
        producer.send("direct:out", exchange);

        consumer.stop();
        producer.stop();
    }


}
