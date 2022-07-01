package org.mycompany;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class MyRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {


        restConfiguration()
                .enableCORS(true)
                .component("jetty")
                .host("0.0.0.0")
                .port(8988)
                .bindingMode(RestBindingMode.json);


        rest()
                .get("/hello")
                .to("direct:hello")
                .get("/hello-kafka")
                .to("direct:hello-kafka");


        from("direct:hello")
                .routeId("GreetingRoute")
                .to("direct:greetStranger");


        from("direct:greetStranger")
                .routeId("greetStranger")
                .setBody(simple("Hello Application!"));


        from("direct:hello-kafka")
                .routeId("KafkaGreetingRoute")
                .to("kafka:myTopic?brokers=my-cluster-kafka-brokers:9092");


        // Kafka Consumer
        from("kafka:myTopic?brokers=my-cluster-kafka-brokers:9092")
                .log("Message received from Kafka : ${body}")
                .log("    on the topic ${headers[kafka.TOPIC]}")
                .log("    on the partition ${headers[kafka.PARTITION]}")
                .log("    with the offset ${headers[kafka.OFFSET]}")
                .log("    with the key ${headers[kafka.KEY]}");


    }

}
