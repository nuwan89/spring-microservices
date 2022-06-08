# Order Manager
 Microservice based system  

## Introduction


## Development guide

### Setting up an embedded h2 DB

**pom.xml**
```xml
<dependency>
	<groupId>com.h2database</groupId>
	<artifactId>h2</artifactId>
	<scope>runtime</scope>
</dependency>
```

### Setting up Eureka for service discovery

**Eureka server project: application.properties**
```properties
server.port=8000  
spring.application.name=discovery-server  

#To disable eureka registering with itself  
eureka.client.register-with-eureka=false  
eureka.client.fetch-registry=false
```

**Enable Eureka in the @SpringBootApplication class**
```Java
@EnableEurekaServer
```
#### Update other eureka microservices
**pom.xml**
```xml
<properties>  
...
 <spring-cloud.version>2021.0.3</spring-cloud.version>  
 ...
</properties>

<dependency>
	<groupId>com.h2database</groupId>
	<artifactId>h2</artifactId>
	<scope>runtime</scope>
</dependency>

<dependencyManagement>  
	<dependencies> 
	 <dependency> 
		 <groupId>org.springframework.cloud</groupId>  
		 <artifactId>spring-cloud-dependencies</artifactId>  
		 <version>${spring-cloud.version}</version>  
		 <type>pom</type>  
		 <scope>import</scope>  
	 </dependency> 
	</dependencies>
</dependencyManagement>
```
**application.properties**
```properties
spring.application.name=order-manager  
server.port=8001  
eureka.client.service-url.defaultZone=http://localhost:8000/eureka
```

**Enable Eureka in the @SpringBootApplication class**
```Java
@EnableEurekaClient
```
