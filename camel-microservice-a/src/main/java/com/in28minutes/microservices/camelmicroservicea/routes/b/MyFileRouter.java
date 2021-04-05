package com.in28minutes.microservices.camelmicroservicea.routes.b;

import java.util.Map;

import org.apache.camel.Body;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


//@Component
public class MyFileRouter extends RouteBuilder {
	
	@Autowired
	private DeciderBean deciderBean;

	@Override
	public void configure() throws Exception {

		from("file:files/input")
		.routeId("File-Input-Route")
		.transform().body(String.class)
		.choice()
			.when(simple("${file:ext} ends with 'xml'"))
				.log("XML FILE")
//			.when(simple("${body} contains 'USD'"))
			.when(method(deciderBean))
				.log("NO an XML FILE but contains USD")
			.otherwise()
				.log("NO an XML FILE")
		.end()
		.log("${body}")
//		.log("${messageHistory}")
//		.to("direct://log-file-values")
		.to("file:files/outut");
		
	}

}

@Component
class DeciderBean {
	
	Logger logger = org.slf4j.LoggerFactory.getLogger(DeciderBean.class);
	
	public boolean isThisConditionMet(@Body String body, @Headers Map<String, String> headers) {
		
		logger.info("DeciderBean {} {}", body, headers);
		
		return true;
	}
	
}
