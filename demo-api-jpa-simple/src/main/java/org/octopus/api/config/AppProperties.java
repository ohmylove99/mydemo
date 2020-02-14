package org.octopus.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Configuration
@ConfigurationProperties(prefix = "octopus", ignoreUnknownFields = false)
public class AppProperties {
	private final Swagger swagger = new Swagger();

	public Swagger getSwagger() {
		return swagger;
	}

	@Getter
	@Setter
	@ToString
	public static class Swagger {
		private String title = AppDefaults.Swagger.title;
		private String description = AppDefaults.Swagger.description;
		private String version = AppDefaults.Swagger.version;
		private String termsOfServiceUrl = AppDefaults.Swagger.termsOfServiceUrl;
		private String contactName = AppDefaults.Swagger.contactName;
		private String contactUrl = AppDefaults.Swagger.contactUrl;
		private String contactEmail = AppDefaults.Swagger.contactEmail;
		private String license = AppDefaults.Swagger.license;
		private String licenseUrl = AppDefaults.Swagger.licenseUrl;
		private String defaultIncludePattern = AppDefaults.Swagger.defaultIncludePattern;
		private String host = AppDefaults.Swagger.host;
		private String[] protocols = AppDefaults.Swagger.protocols;
		private boolean useDefaultResponseMessages = AppDefaults.Swagger.useDefaultResponseMessages;
	}
}
