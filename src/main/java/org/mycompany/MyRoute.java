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
         .port(8989)
         .bindingMode(RestBindingMode.json);



      rest()
         .get("/hello")
         .to("direct:hello");
      
      
     from("direct:hello")
     .routeId("GreetingRoute")
     	.choice()
     	  .when(simple("${header.name} == 'abhishek'"))
     	  .to("direct:greetAbhishek")
     	  .otherwise()
     	 .to("direct:greetStranger");
     
     
     from("direct:greetAbhishek")
     	.routeId("greetAbhishek")
     	.setBody(simple("hello Abhishek..!"));
     
     from("direct:greetStranger")
	  	.routeId("greetStranger")
	  	.setBody(simple("hello Stranger..!"));
  
     


	    
	 
	 }

}
