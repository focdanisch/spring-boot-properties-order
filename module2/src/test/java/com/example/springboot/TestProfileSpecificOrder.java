package com.example.springboot;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

@SpringBootTest
public class TestProfileSpecificOrder {

	@Value("${test.prop}")
	String testProp;

	@Autowired
	Environment environment;

	@Test
	public void testValue() {
		// application-root must override application-config and all application-locals
		// works with 2.3.10.RELEASE
		// fails with 2.4.5
		assertThat(testProp).isEqualTo("root");
	}

	@Test
	public void testCorrectPropertyOrder() {
		List<String> appPropertyNames = ((AbstractEnvironment) environment).getPropertySources().stream()
				.filter(prop -> prop instanceof OriginTrackedMapPropertySource)
				.map(PropertySource::getName)
				.collect(Collectors.toList());
		assertThat(appPropertyNames).hasSize(7);
		assertThat(appPropertyNames.get(0))
			.contains("classpath")
			.doesNotContain("config")
			.contains("application-root.properties");
		assertThat(appPropertyNames.get(1))
			.contains("file")
			.contains("config")
			.contains("application-config.properties");
		assertThat(appPropertyNames.get(2))
			.contains("classpath")
			.contains("config")
			.contains("application-config.properties");
		assertThat(appPropertyNames.get(3))
			.contains("file")
			.contains("config")
			.contains("application-local.properties");
		assertThat(appPropertyNames.get(4))
			.contains("classpath")
			.contains("config")
			.contains("application-local.properties");
		assertThat(appPropertyNames.get(5))
			.contains("classpath")
			.doesNotContain("config")
			.contains("application-local.properties");
		assertThat(appPropertyNames.get(6))
			.contains("classpath")
			.doesNotContain("config")
			.contains("application.properties");
	}
}
