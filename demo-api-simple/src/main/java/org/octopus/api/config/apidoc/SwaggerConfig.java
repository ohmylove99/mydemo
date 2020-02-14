package org.octopus.api.config.apidoc;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.octopus.api.config.AppConstants;
import org.octopus.api.config.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile("!" + AppConstants.SPRING_PROFILE_PRODUCTION)
@Slf4j
public class SwaggerConfig {
	@Autowired
	private AppProperties appProperties;

	@Bean
	public Docket api() {
		log.debug("Starting Swagger");
		StopWatch watch = new StopWatch();
		watch.start();
		
		Contact contact = new Contact(appProperties.getSwagger().getContactName(),
				appProperties.getSwagger().getContactUrl(), appProperties.getSwagger().getContactEmail());
		@SuppressWarnings("rawtypes")
		List<VendorExtension> vendorExtensions = new ArrayList<>();

		ApiInfo apiInfo = new ApiInfo(appProperties.getSwagger().getTitle(),
				appProperties.getSwagger().getDescription(), appProperties.getSwagger().getVersion(),
				appProperties.getSwagger().getTermsOfServiceUrl(), contact, appProperties.getSwagger().getLicense(),
				appProperties.getSwagger().getLicenseUrl(), vendorExtensions);

		Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo)
				.genericModelSubstitutes(ResponseEntity.class).forCodeGeneration(true)
				.genericModelSubstitutes(ResponseEntity.class)
				.directModelSubstitute(org.joda.time.LocalDate.class, String.class)
				.directModelSubstitute(org.joda.time.LocalDateTime.class, Date.class)
				.directModelSubstitute(org.joda.time.DateTime.class, Date.class)
				.directModelSubstitute(java.time.LocalDate.class, String.class)
				.directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
				.directModelSubstitute(java.time.LocalDateTime.class, Date.class).select()
				.paths(regex(appProperties.getSwagger().getDefaultIncludePattern())).build();
		watch.stop();
		log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
		return docket;
	}
}