/*
* Copyright (c) 2017 Capital One Financial Corporation All Rights Reserved.
*
* This software contains valuable trade secrets and proprietary information of
* Capital One and is protected by law. It may not be copied or distributed in
* any form or medium, disclosed to third parties, reverse engineered or used in
* any manner without prior written authorization from Capital One.
*
*
*/
package hello;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/*
*
* @Configuration tags the class as a source of bean definitions for the application context.
*
* @EnableAutoConfiguration tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
*
* Normally you would add @EnableWebMvc for a Spring MVC app, but Spring Boot adds it automatically when it sees spring-webmvc on the classpath.
* This flags the applicationas a web application and activates key behaviors such as setting up a DispatcherServlet.
*
* @ComponentScan tells Spring to look for other components, configurations, and services in the hello package, allowing it to find the controllers.
*
*/

@SpringBootApplication //@SpringBootApplication is a convenience annotation that adds all of the above.
public class Application {
	
	// Main method
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	//The CommandLineRunner method is marked as a @Bean and this runs on start up.
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		
		//TODO: Refactor this code to print out the beans in a sorted order.
		return args -> {  //Let's inspect the beans provided by Spring Boot
		
			System.out.println("Let's inspect the beans provided by Spring Boot:");
			
			String[] beanNames = ctx.getBeanDefinitionNames();
//            Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
			
		};
	}
	
}