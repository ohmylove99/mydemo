package org.octopus.api.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This is bean of model mapper
 * 
 * @author joshualeng
 *
 */
@Configuration
public class ModelMapperConfig {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
