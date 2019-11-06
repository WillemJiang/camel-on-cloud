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
