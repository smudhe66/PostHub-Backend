package com.sm.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@io.swagger.v3.oas.annotations.security.SecurityScheme(
		  name = "Bearer Authentication",
		  type = SecuritySchemeType.HTTP,
		  bearerFormat = "JWT",
		  scheme = "bearer"
		)
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		final String securitySchemeName = "JwtAuth";

		return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
				.components(new Components().addSecuritySchemes(securitySchemeName,
						new SecurityScheme().name(securitySchemeName)
						.type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER)
                        .name("Auth"))) 
				
				.info(new Info().title("Blog App").description("This project is develeoped using spring boot")
						.version("1.0")
						.contact(new Contact().name("Suresh").email("suresh@gmail.com").url("suresh.com"))
						.license(new License().name("License of APIs")))
				.externalDocs(new ExternalDocumentation().url("blogapp.com").description("This is external url"));
	}

//	@Bean
//	public Docket api() {
//		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo()).select().apis(RequestHandlerSelectors.any())
//				.paths(PathSelectors.any()).build();
//	}
//
//	private ApiInfo getInfo() {
//		// TODO Auto-generated method stub
//		return new ApiInfo("Blogging Application", "This project is develeoped using spring boot", "1.0",
//				"Terms of service", new Contact("Suresh", "website link", "suresh@gmail.com"), "License of APIS",
//				"API license URL", Collections.emptyList());
//	};
//	
//	 @Bean
//	    public Docket api() {
//	        return new Docket(DocumentationType.OAS_30)
//	                .apiInfo(apiInfo())
//	                .select()
//	                .apis(RequestHandlerSelectors.basePackage("com.sm.blog")) // Replace with your base package
//	                .build();
//	    }
//
//	    private ApiInfo apiInfo() {
//	        return new ApiInfoBuilder()
//	                .title("Blogging Application API")
//	                .description("APIs for managing a blogging application")
//	                .version("1.0")
//	                .contact(new Contact("Suresh", "website link", "suresh@gmail.com"))
//	                .license("API License")
//	                .licenseUrl("API license URL")
//	                .build();
//	    }
}
