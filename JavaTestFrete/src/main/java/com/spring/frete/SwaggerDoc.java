package com.spring.frete;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;



// documentação via swagger
@Configuration
public class SwaggerDoc {
	
	@Bean
	public Docket docApi() {
		return new Docket(DocumentationType.SWAGGER_2)
		        .select() 
		        .apis(RequestHandlerSelectors.basePackage("com.spring.frete.controller")) 
		        .paths(PathSelectors.any()) 
		        .build()
		        .apiInfo(apiInfo())
		        .useDefaultResponseMessages(false);
	}
	
	public ApiInfo apiInfo() {
		return new ApiInfo(
				"Documentação API",
				"Calcula o valor total do frete e a data prevista da entrega",
				"API v.1",
				"Termos",
				new Contact("Otávio Belfort", "https://github.com/otaviobelfort","otaviodeel@gmail.com"),
				"Apache License 2.0","https://www.apache.org/licenses/LICENSE-2.0",Collections.emptyList());
	}

}

