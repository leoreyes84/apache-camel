<!---
Current Directory : /in28Minutes/git/camel/02.projects
-->

## Complete Code Example


### /docker-compose.yaml

```
version: '2'

services:
  zookeeper:
    image: 'docker.io/bitnami/zookeeper:3-debian-10'
    ports:
      - '2181:2181'
    volumes:
      - 'zookeeper_data:/bitnami'
    environment:
      - ALLOW_ANONYMOUS_LOGIN=yes
  kafka:
    image: 'docker.io/bitnami/kafka:2-debian-10'
    ports:
      - '9092:9092'
    volumes:
      - 'kafka_data:/bitnami'
    environment:
      - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181
      - ALLOW_PLAINTEXT_LISTENER=yes
    depends_on:
      - zookeeper

volumes:
  zookeeper_data:
    driver: local
  kafka_data:
    driver: local
```
---

### /camel-microservice-b/pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.4.2</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.in28minutes.microservices</groupId>
	<artifactId>camel-microservice-b</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>camel-microservice-b</name>
	<description>Demo project for Camel</description>

	<properties>
		<java.version>15</java.version>
		<camel.version>3.7.0</camel.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.apache.camel.springboot</groupId>
			<artifactId>camel-spring-boot-starter</artifactId>
			<version>${camel.version}</version>
		</dependency>

		<dependency>
			<groupId>org.apache.camel.springboot</groupId>
			<artifactId>camel-activemq-starter</artifactId>
			<version>${camel.version}</version>
		</dependency>

		<dependency>
			<groupId>org.apache.camel.springboot</groupId>
			<artifactId>camel-kafka-starter</artifactId>
			<version>${camel.version}</version>
		</dependency>

		<dependency>
			<groupId>org.apache.camel.springboot</groupId>
			<artifactId>camel-crypto-starter</artifactId>
			<version>${camel.version}</version>
		</dependency>

		<dependency>
			<groupId>org.apache.camel.springboot</groupId>
			<artifactId>camel-jackson-starter</artifactId>
			<version>${camel.version}</version>
		</dependency>

		<dependency>
			<groupId>org.apache.camel.springboot</groupId>
			<artifactId>camel-jacksonxml-starter</artifactId>
			<version>${camel.version}</version>
		</dependency>


		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```
---

### /camel-microservice-b/src/test/java/com/in28minutes/microservices/camelmicroserviceb/CamelMicroserviceBApplicationTests.java

```java
package com.in28minutes.microservices.camelmicroserviceb;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CamelMicroserviceBApplicationTests {

	@Test
	void contextLoads() {
	}

}
```
---

### /camel-microservice-b/src/main/resources/myDesKey.jceks

```
????????????          mydeskey  wV???,?????? sr 3com.sun.crypto.provider.SealedObjectForKeyProtector???W???Y???0???S  xr javax.crypto.SealedObject>6=?????Tp [ 
encodedParamst [B[ encryptedContentq ~ L 	paramsAlgt Ljava/lang/String;L sealAlgq ~ xpur [B?????????T???  xp   0`]???] oC
@uq ~   Q???;??????G`??????X??????o????????????;???Kc???V??????-???s????????????8???v???.}???e;???y,j??????T ???e?????????+??????r???7??????i?????????x???s???X?G\q?????????Y=??????'???w1???|???vwi??????6?????????M??????-???????????j?????????kS????P,???%???28PU<?????????U???`?????????d??????Y?????????|???xY?????q???T???s??????o?????????F??????Ag?????-???mu????????????9??????????????????7???1????????????obI???	]??????wk???&Nfo??????D???4?????????????????????Mv?????????u??t PBEWithMD5AndTripleDESt PBEWithMD5AndTripleDESn???4??????'??????????R??????Qw???
```
---

### /camel-microservice-b/src/main/resources/application.properties

```properties
server.port=8000
spring.activemq.broker-url=tcp://localhost:61616
camel.component.kafka.brokers=localhost:9092
```
---

### /camel-microservice-b/src/main/java/com/in28minutes/microservices/camelmicroserviceb/CamelMicroserviceBApplication.java

```java
package com.in28minutes.microservices.camelmicroserviceb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CamelMicroserviceBApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamelMicroserviceBApplication.class, args);
	}

}
```
---

### /camel-microservice-b/src/main/java/com/in28minutes/microservices/camelmicroserviceb/CurrencyExchange.java

```java
package com.in28minutes.microservices.camelmicroserviceb;

import java.math.BigDecimal;

public class CurrencyExchange {
	private Long id;
	private String from;
	private String to;
	private BigDecimal conversionMultiple;

	public CurrencyExchange() {

	}

	public CurrencyExchange(Long id, String from, String to, BigDecimal conversionMultiple) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.conversionMultiple = conversionMultiple;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public BigDecimal getConversionMultiple() {
		return conversionMultiple;
	}

	public void setConversionMultiple(BigDecimal conversionMultiple) {
		this.conversionMultiple = conversionMultiple;
	}

	@Override
	public String toString() {
		return "CurrencyExchange [id=" + id + ", from=" + from + ", to=" + to + ", conversionMultiple="
				+ conversionMultiple + "]";
	}

}
```
---

### /camel-microservice-b/src/main/java/com/in28minutes/microservices/camelmicroserviceb/CurrencyExchangeController.java

```java

package com.in28minutes.microservices.camelmicroserviceb;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange findConversionValue(
			@PathVariable String from,
			@PathVariable String to
			) {
		return new CurrencyExchange(10001L,from,to, BigDecimal.TEN);
	}

}
```
---

### /camel-microservice-b/src/main/java/com/in28minutes/microservices/camelmicroserviceb/routes/KafkaReceiverRouter.java

```java
package com.in28minutes.microservices.camelmicroserviceb.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class KafkaReceiverRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("kafka:myKafkaTopic")
		.to("log:received-message-from-kafka");

	}

}
```
---

### /camel-microservice-b/src/main/java/com/in28minutes/microservices/camelmicroserviceb/routes/ActiveMqReceiverRouter.java

```java
package com.in28minutes.microservices.camelmicroserviceb.routes;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.crypto.CryptoDataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.in28minutes.microservices.camelmicroserviceb.CurrencyExchange;

@Component
public class ActiveMqReceiverRouter extends RouteBuilder {

	@Autowired
	private MyCurrencyExchangeProcessor myCurrencyExchangeProcessor;

	@Autowired
	private MyCurrencyExchangeTransformer myCurrencyExchangeTransformer;

	@Override
	public void configure() throws Exception {

		from("activemq:my-activemq-queue")
		.unmarshal(createEncryptor())
//		.unmarshal()
//		.json(JsonLibrary.Jackson, CurrencyExchange.class)
//		.bean(myCurrencyExchangeProcessor)
//		.bean(myCurrencyExchangeTransformer)
				.to("log:received-message-from-active-mq");

//		from("activemq:my-activemq-xml-queue")
//		.unmarshal()
//		.jacksonxml(CurrencyExchange.class)
//		.to("log:received-message-from-active-mq");

//		from("activemq:split-queue")
//		.to("log:received-message-from-active-mq");

	}

	private CryptoDataFormat createEncryptor() throws KeyStoreException, IOException, NoSuchAlgorithmException,
			CertificateException, UnrecoverableKeyException {
		KeyStore keyStore = KeyStore.getInstance("JCEKS");
		ClassLoader classLoader = getClass().getClassLoader();
		keyStore.load(classLoader.getResourceAsStream("myDesKey.jceks"), "someKeystorePassword".toCharArray());
		Key sharedKey = keyStore.getKey("myDesKey", "someKeyPassword".toCharArray());

		CryptoDataFormat sharedKeyCrypto = new CryptoDataFormat("DES", sharedKey);
		return sharedKeyCrypto;
	}

}

@Component
class MyCurrencyExchangeProcessor {

	Logger logger = LoggerFactory.getLogger(MyCurrencyExchangeProcessor.class);

	public void processMessage(CurrencyExchange currencyExchange) {

		logger.info("Do some processing wiht currencyExchange.getConversionMultiple() value which is {}",
				currencyExchange.getConversionMultiple());

	}
}

@Component
class MyCurrencyExchangeTransformer {

	Logger logger = LoggerFactory.getLogger(MyCurrencyExchangeProcessor.class);

	public CurrencyExchange processMessage(CurrencyExchange currencyExchange) {

		currencyExchange.setConversionMultiple(currencyExchange.getConversionMultiple().multiply(BigDecimal.TEN));

		return currencyExchange;

	}
}
```
---

### /camel-microservice-a/pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.4.2</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.in28minutes.microservices</groupId>
	<artifactId>camel-microservice-a</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>camel-microservice-a</name>
	<description>Demo project for Camel</description>

	<properties>
		<java.version>15</java.version>
		<camel.version>3.7.0</camel.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.apache.camel.springboot</groupId>
			<artifactId>camel-spring-boot-starter</artifactId>
			<version>${camel.version}</version>
		</dependency>

		<dependency>
			<groupId>org.apache.camel.springboot</groupId>
			<artifactId>camel-activemq-starter</artifactId>
			<version>${camel.version}</version>
		</dependency>

		<dependency>
			<groupId>org.apache.camel.springboot</groupId>
			<artifactId>camel-kafka-starter</artifactId>
			<version>${camel.version}</version>
		</dependency>

		<dependency>
			<groupId>org.apache.camel.springboot</groupId>
			<artifactId>camel-crypto-starter</artifactId>
			<version>${camel.version}</version>
		</dependency>

		<dependency>
			<groupId>org.apache.camel.springboot</groupId>
			<artifactId>camel-jackson-starter</artifactId>
			<version>${camel.version}</version>
		</dependency>

		<dependency>
			<groupId>org.apache.camel.springboot</groupId>
			<artifactId>camel-http-starter</artifactId>
			<version>${camel.version}</version>
		</dependency>

		<dependency>
			<groupId>org.apache.camel.springboot</groupId>
			<artifactId>camel-csv-starter</artifactId>
			<version>${camel.version}</version>
		</dependency>


		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```
---

### /camel-microservice-a/files/output/1002.json

```json
{
  "id": 1002,
  "from": "AUD",
  "to": "INR",
  "conversionMultiple": 10
}
```
---

### /camel-microservice-a/files/output/1000.json

```json
{
  "id": 1000,
  "from": "USD",
  "to": "INR",
  "conversionMultiple": 70
}
```
---

### /camel-microservice-a/files/output/1001.json

```json
{
  "id": 1001,
  "from": "EUR",
  "to": "INR",
  "conversionMultiple": 80
}
```
---

### /camel-microservice-a/files/output/single-line.csv

```
"id","from","to","conversionMultiple"
```
---

### /camel-microservice-a/files/output/1001.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<root>
   <id>1001</id>
   <from>EUR</from>
   <to>INR</to>
   <conversionMultiple>80</conversionMultiple>
</root>
```
---

### /camel-microservice-a/files/output/1000.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<root>
   <id>1000</id>
   <from>USD</from>
   <to>INR</to>
   <conversionMultiple>70</conversionMultiple>
</root>
```
---

### /camel-microservice-a/src/test/java/com/in28minutes/microservices/camelmicroservicea/CamelMicroserviceAApplicationTests.java

```java
package com.in28minutes.microservices.camelmicroservicea;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CamelMicroserviceAApplicationTests {

	@Test
	void contextLoads() {
	}

}
```
---

### /camel-microservice-a/src/main/resources/myDesKey.jceks

```
????????????          mydeskey  wV???,?????? sr 3com.sun.crypto.provider.SealedObjectForKeyProtector???W???Y???0???S  xr javax.crypto.SealedObject>6=?????Tp [ 
encodedParamst [B[ encryptedContentq ~ L 	paramsAlgt Ljava/lang/String;L sealAlgq ~ xpur [B?????????T???  xp   0`]???] oC
@uq ~   Q???;??????G`??????X??????o????????????;???Kc???V??????-???s????????????8???v???.}???e;???y,j??????T ???e?????????+??????r???7??????i?????????x???s???X?G\q?????????Y=??????'???w1???|???vwi??????6?????????M??????-???????????j?????????kS????P,???%???28PU<?????????U???`?????????d??????Y?????????|???xY?????q???T???s??????o?????????F??????Ag?????-???mu????????????9??????????????????7???1????????????obI???	]??????wk???&Nfo??????D???4?????????????????????Mv?????????u??t PBEWithMD5AndTripleDESt PBEWithMD5AndTripleDESn???4??????'??????????R??????Qw???
```
---

### /camel-microservice-a/src/main/resources/application.properties

```properties
spring.activemq.broker-url=tcp://localhost:61616
camel.component.kafka.brokers=localhost:9092
endpoint-for-logging=log:directendpoint1
timePeriod=7000

#camel.springboot.main-run-controller = true
#camel.springboot.duration-max-seconds = 60

#logging.level.org.springframework = INFO
#logging.level.org.apache.camel.spring.boot = INFO
#logging.level.org.springframework.amqp.rabbit = DEBUG
logging.level.org.apache.camel.impl = DEBUG

#camel.component.activemq.password
#camel.component.activemq.username
```
---

### /camel-microservice-a/src/main/java/com/in28minutes/microservices/camelmicroservicea/CurrencyExchange.java

```java
package com.in28minutes.microservices.camelmicroservicea;

import java.math.BigDecimal;

public class CurrencyExchange {
	private Long id;
	private String from;
	private String to;
	private BigDecimal conversionMultiple;

	public CurrencyExchange() {

	}

	public CurrencyExchange(Long id, String from, String to, BigDecimal conversionMultiple) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.conversionMultiple = conversionMultiple;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public BigDecimal getConversionMultiple() {
		return conversionMultiple;
	}

	public void setConversionMultiple(BigDecimal conversionMultiple) {
		this.conversionMultiple = conversionMultiple;
	}

	@Override
	public String toString() {
		return "CurrencyExchange [id=" + id + ", from=" + from + ", to=" + to + ", conversionMultiple="
				+ conversionMultiple + "]";
	}

}
```
---

### /camel-microservice-a/src/main/java/com/in28minutes/microservices/camelmicroservicea/CamelMicroserviceAApplication.java

```java
package com.in28minutes.microservices.camelmicroservicea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CamelMicroserviceAApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamelMicroserviceAApplication.class, args);
	}

}
```
---

### /camel-microservice-a/src/main/java/com/in28minutes/microservices/camelmicroservicea/routes/patterns/EipPatternsRouter.java

```java
package com.in28minutes.microservices.camelmicroservicea.routes.patterns;

import java.util.List;
import java.util.Map;

import org.apache.camel.Body;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.in28minutes.microservices.camelmicroservicea.CurrencyExchange;

//@Component
public class EipPatternsRouter extends RouteBuilder{
	

	@Autowired
	SplitterComponent splitter;
	
	
	@Autowired
	DynamicRouterBean dynamicRouterBean;

	@Override
	public void configure() throws Exception {

		getContext().setTracing(true);
		
		errorHandler(deadLetterChannel("activemq:dead-letter-queue"));

		//Pipeline
		//Content Based Routing - choice()
		//Multicast
		
		
//		from("timer:multicast?period=10000")
//		.multicast()
//		.to("log:something1", "log:something2", "log:something3");
		
//		from("file:files/csv")
//		.unmarshal().csv()
//		.split(body())
//		.to("activemq:split-queue");
		
		//Message,Message2,Message3
//		from("file:files/csv")
//		.convertBodyTo(String.class)
//		//.split(body(),",")
//		.split(method(splitter))
//		.to("activemq:split-queue");
		
		//Aggregate
		//Messages => Aggregate => Endpoint
		//to , 3
		from("file:files/aggregate-json")
		.unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
		.aggregate(simple("${body.to}"), new ArrayListAggregationStrategy())
		.completionSize(3)
		//.completionTimeout(HIGHEST)
		.to("log:aggregate-json");
		
		String routingSlip = "direct:endpoint1,direct:endpoint3";
		//String routingSlip = "direct:endpoint1,direct:endpoint2,direct:endpoint3";
		
//		from("timer:routingSlip?period=10000")
//		.transform().constant("My Message is Hardcoded")
//		.routingSlip(simple(routingSlip));
		
		//Dynamic Routing
		
		//Step 1, Step 2, Step 3

		from("timer:dynamicRouting?period={{timePeriod}}")
		.transform().constant("My Message is Hardcoded")
		.dynamicRouter(method(dynamicRouterBean));

		
		//Endpoint1
		//Endpoint2
		//Endpoint3

		
		from("direct:endpoint1")
		.wireTap("log:wire-tap") //add
		.to("{{endpoint-for-logging}}");

		from("direct:endpoint2")
		.to("log:directendpoint2");

		from("direct:endpoint3")
		.to("log:directendpoint3");


	}

}

@Component
class SplitterComponent{
	public List<String> splitInput(String body){
		return List.of("ABC", "DEF", "GHI");
	}
}

@Component
class DynamicRouterBean{
	
	Logger logger = LoggerFactory.getLogger(DynamicRouterBean.class);
	
	int invocations ;
	
	public String decideTheNextEndpoint(
				@ExchangeProperties Map<String, String> properties,
				@Headers Map<String, String> headers,
				@Body String body
			) {
		logger.info("{} {} {}", properties, headers, body);
		
		invocations++;
		
		if(invocations%3==0)
			return "direct:endpoint1";
		
		if(invocations%3==1)
			return "direct:endpoint2,direct:endpoint3";
		
		return null;
			
		
	}
}
```
---

### /camel-microservice-a/src/main/java/com/in28minutes/microservices/camelmicroservicea/routes/patterns/ArrayListAggregationStrategy.java

```java
package com.in28minutes.microservices.camelmicroservicea.routes.patterns;

import java.util.ArrayList;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

public class ArrayListAggregationStrategy implements AggregationStrategy {
	//1,2,3

	//null, 1 => [1]
	//[1], 2 => [1,2]
	//[1,2], 3 => [1,2,3]
	
	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        Object newBody = newExchange.getIn().getBody();
        ArrayList<Object> list = null;
        if (oldExchange == null) {
            list = new ArrayList<Object>();
            list.add(newBody);
            newExchange.getIn().setBody(list);
            return newExchange;
        } else {
            list = oldExchange.getIn().getBody(ArrayList.class);
            list.add(newBody);
            return oldExchange;
        }
    }
}
```
---

### /camel-microservice-a/src/main/java/com/in28minutes/microservices/camelmicroservicea/routes/a/MyFirstTimerRouter.java

```java
package com.in28minutes.microservices.camelmicroservicea.routes.a;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class MyFirstTimerRouter extends RouteBuilder{
	
	@Autowired
	private GetCurrentTimeBean getCurrentTimeBean; 
	
	@Autowired
	private SimpleLoggingProcessingComponent loggingComponent;

	@Override
	public void configure() throws Exception {
		// timer
		// transformation
		// log
		// Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
		from("timer:first-timer") //null
		.log("${body}")//null
		.transform().constant("My Constant Message")
		.log("${body}")//My Constant Message
		//.transform().constant("Time now is" + LocalDateTime.now())
		//.bean("getCurrentTimeBean")

		//Processing
		//Transformation
		
		.bean(getCurrentTimeBean)
		.log("${body}")//Time now is2021-01-18T18:32:19.660244
		.bean(loggingComponent)
		.log("${body}")
		.process(new SimpleLoggingProcessor())
		.to("log:first-timer"); //database
		

	}

}

@Component
class GetCurrentTimeBean {
	public String getCurrentTime() {
		return "Time now is" + LocalDateTime.now();
	}
}

@Component
class SimpleLoggingProcessingComponent {
	
	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

	public void process(String message) {
		
		logger.info("SimpleLoggingProcessingComponent {}", message);
		
	}
}


class SimpleLoggingProcessor implements Processor {
	
	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("SimpleLoggingProcessor {}", exchange.getMessage().getBody());
	}

}
```
---

### /camel-microservice-a/src/main/java/com/in28minutes/microservices/camelmicroservicea/routes/c/ActiveMqSenderRouter.java

```java
package com.in28minutes.microservices.camelmicroservicea.routes.c;

import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.crypto.CryptoDataFormat;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqSenderRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		// timer
		from("timer:active-mq-timer?period=10000")
		.transform().constant("My message for Active MQ").log("${body}")
		.marshal(createEncryptor())
		.to("activemq:my-activemq-queue");
		// queue

//		from("file:files/json")
//		.log("${body}")
//		.to("activemq:my-activemq-queue");

//		from("file:files/xml")
//		.log("${body}")
//		.to("activemq:my-activemq-xml-queue");

	}

	private CryptoDataFormat createEncryptor() throws KeyStoreException, IOException, NoSuchAlgorithmException,
			CertificateException, UnrecoverableKeyException {
		KeyStore keyStore = KeyStore.getInstance("JCEKS");
		ClassLoader classLoader = getClass().getClassLoader();
		keyStore.load(classLoader.getResourceAsStream("myDesKey.jceks"), 
				"someKeystorePassword".toCharArray());
		Key sharedKey = keyStore.getKey("myDesKey", "someKeyPassword".toCharArray());

		CryptoDataFormat sharedKeyCrypto = new CryptoDataFormat("DES", sharedKey);
		return sharedKeyCrypto;
	}

}
```
---

### /camel-microservice-a/src/main/java/com/in28minutes/microservices/camelmicroservicea/routes/c/RestApiConsumerRouter.java

```java
package com.in28minutes.microservices.camelmicroservicea.routes.c;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class RestApiConsumerRouter extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		restConfiguration().host("localhost").port(8000);
		
		from("timer:rest-api-consumer?period=10000")
		.setHeader("from", () -> "EUR")
		.setHeader("to", () -> "INR")
		.log("${body}")
		.to("rest:get:/currency-exchange/from/{from}/to/{to}")
		.log("${body}");
		
	}

}
```
---

### /camel-microservice-a/src/main/java/com/in28minutes/microservices/camelmicroservicea/routes/c/KafkaSenderRouter.java

```java
package com.in28minutes.microservices.camelmicroservicea.routes.c;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class KafkaSenderRouter extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		from("file:files/json")
		.log("${body}")
		.to("kafka:myKafkaTopic");
		
	}

}
```
---

### /camel-microservice-a/src/main/java/com/in28minutes/microservices/camelmicroservicea/routes/b/MyFileRouter.java

```java
package com.in28minutes.microservices.camelmicroservicea.routes.b;

import java.util.Map;

import org.apache.camel.Body;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class MyFileRouter extends RouteBuilder{
	
	@Autowired
	private DeciderBean deciderBean;

	@Override
	public void configure() throws Exception {

		//Pipeline
		
		from("file:files/input")
		//.pipeline()
		.routeId("Files-Input-Route")
		.transform().body(String.class)
		.choice() //Content Based Routing
			.when(simple("${file:ext} == 'xml'"))
				.log("XML FILE")
			//.when(simple("${body} contains 'USD'"))
			.when(method(deciderBean))
				.log("Not an XML FILE BUT contains USD")
			.otherwise()
				.log("Not an XML FILE")
		.end()
		.to("file:files/output");
		
		from("direct:log-file-values")
		.log("${messageHistory} ${file:absolute.path}")
	   	.log("${file:name} ${file:name.ext} ${file:name.noext} ${file:onlyname}")
	   	.log("${file:onlyname.noext} ${file:parent} ${file:path} ${file:absolute}")
	   	.log("${file:size} ${file:modified}")
	   	.log("${routeId} ${camelId} ${body}");

	}

}

@Component
class DeciderBean {
	
	Logger logger = LoggerFactory.getLogger(DeciderBean.class);
	
	public boolean isThisConditionMet(@Body String body,
			@Headers Map<String,String> headers,
			@ExchangeProperties Map<String,String> exchangeProperties) {
		
		logger.info("DeciderBean {} {} {}", body, headers, exchangeProperties);
		return true;
	}
}
```
---
