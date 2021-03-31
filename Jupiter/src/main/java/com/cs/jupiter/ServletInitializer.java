package com.cs.jupiter;

import javax.sql.DataSource;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(JupiterApplication.class);
	}

	@Bean
	@ConfigurationProperties("spring.datasource")
	public DataSource dataSource() {
		return  DataSourceBuilder.create().build();
	}
}
