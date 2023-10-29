package config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Provides startup configuration of the Spring Boot application.
 */
@ConfigurationPropertiesScan
@SpringBootApplication(scanBasePackages = { "config", "dev.workingtheory" })
public class Application
		extends SpringBootServletInitializer
{
	/**
	 * Configures {@link Application} as the source of application configuration.
	 * {@code @SpringBootApplication} provides additional configuration locations.
	 *
	 * @param builder {@link SpringApplicationBuilder} instance.
	 * @return configured builder instance.
	 */
	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder)
	{
		return builder.sources(Application.class);
	}

	/**
	 * Application starting point.
	 *
	 * @param args program arguments.
	 */
	public static void main(final String[] args)
	{
		// Creating spring application
		final SpringApplication application = new SpringApplication(Application.class);

		// Starting the application
		application.run(args);
	}
}
