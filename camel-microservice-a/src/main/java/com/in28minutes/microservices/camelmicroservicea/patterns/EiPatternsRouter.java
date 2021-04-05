package com.in28minutes.microservices.camelmicroservicea.patterns;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component//EnterpriseIntegrationPatterns
public class EiPatternsRouter extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		//*****Pipeline (.pipeline() by default)
		//*****Content Based Routing (.choice())
		//*****Multicast (.multicast())
		
//		from("timer:multicast?period=10000")
//		.multicast()
//		.to("log:something1", "log:something2");
		
		//******Split -> send as many messages
//		from("file:files/csv")
//		.unmarshal().csv()
//		.split(body())
//		.to("log:split-files");
		
		from("file:files/csv")
		.convertBodyTo(String.class)
		.split(body())
		.to("log:split-files");
		
		//******Aggregate -> Send as one message based on an attribute
	}

}
