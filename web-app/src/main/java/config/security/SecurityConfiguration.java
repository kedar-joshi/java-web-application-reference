package config.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration
{
	private static final Logger logger = LogManager.getLogger(SecurityConfiguration.class);


	@Bean("apiRequestMatcher")
	public MvcRequestMatcher.Builder mvc(final HandlerMappingIntrospector configuration)
	{
		return new MvcRequestMatcher.Builder(configuration).servletPath("/api");
	}

	@Bean("apiSecurityFilterChain")
	public SecurityFilterChain configure(final HttpSecurity http,
										 final @Qualifier("apiRequestMatcher") MvcRequestMatcher.Builder mvc) throws Exception
	{
		// Configuring session management
		http.sessionManagement(SecurityConfiguration::configureSessionManagement);

		// Disabling CSRF protection due to stateless authentication
		http.csrf(AbstractHttpConfigurer::disable);

		// Configuring CORS with default configuration
		http.cors(Customizer.withDefaults());

		// Configuring HTTP authentication rules and exceptions
		http.authorizeHttpRequests(registry -> configureHttpRequestAuthorization(registry, mvc));

		logger.info("Security configuration completed.");

		return http.build();
	}

	/**
	 * Configures session management.
	 *
	 * @param configurer Session management configurer.
	 */
	private static void configureSessionManagement(final SessionManagementConfigurer<HttpSecurity> configurer)
	{
		// Configuring stateless session management
		configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	/**
	 * Configures HTTP authentication rules and exceptions.
	 *
	 * @param registry HTTP authentication rules registry.
	 * @param mvc      MVC request matcher builder.
	 */
	private static void configureHttpRequestAuthorization(final AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry registry,
														  final MvcRequestMatcher.Builder mvc)
	{
		// Allowing only POST requests for user login
		// registry.requestMatchers(mvc.pattern(HttpMethod.POST, "/authentications/login")).permitAll();
		// registry.requestMatchers(mvc.pattern(HttpMethod.GET, "/greet/**")).permitAll();

		// Allowing static resources
		// registry.requestMatchers("/static/**").permitAll();

		// Allowing static resources
		// registry.requestMatchers("/monitoring/**").permitAll();

		// Allowing API endpoints to be authenticated
		// registry.requestMatchers("/**").authenticated();

		// Allow all other requests
		registry.anyRequest().permitAll();
	}
}
