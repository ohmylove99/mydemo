package org.octopus.api;

import org.octopus.OctopusDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import springfox.documentation.service.Contact;

/**
 * 
 * @author joshualeng
 *
 */
@Configuration
@ConfigurationProperties(prefix = "common", ignoreUnknownFields = false)
public class CommonProperties {
	private final Swagger swagger = new Swagger();

	public Swagger getSwagger() {
		return swagger;
	}

	@Getter
	@Setter
	@ToString
	public static class Swagger {
		private String title = OctopusDefaults.Swagger.title;
		private String description = OctopusDefaults.Swagger.description;
		private String version = OctopusDefaults.Swagger.version;
		private String termsOfServiceUrl = OctopusDefaults.Swagger.termsOfServiceUrl;
		private String contactName = OctopusDefaults.Swagger.contactName;
		private String contactUrl = OctopusDefaults.Swagger.contactUrl;
		private String contactEmail = OctopusDefaults.Swagger.contactEmail;

		private String license = OctopusDefaults.Swagger.license;
		private String licenseUrl = OctopusDefaults.Swagger.licenseUrl;
		private String defaultIncludePattern = OctopusDefaults.Swagger.defaultIncludePattern;
		private String host = OctopusDefaults.Swagger.host;
		private String[] protocols = OctopusDefaults.Swagger.protocols;
		private boolean useDefaultResponseMessages = OctopusDefaults.Swagger.useDefaultResponseMessages;

		public Contact getContact() {
			Contact contact = new Contact(getContactName(), getContactUrl(), getContactEmail());
			return contact;
		}
	}
}
