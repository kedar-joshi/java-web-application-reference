package config.serialization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.jakarta.xmlbind.JakartaXmlBindAnnotationModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;

/**
 * Provides configuration for JSON and XML serialization with Jackson serializers and respective message converters.
 */
@Configuration
public class JacksonConfiguration
{
	/**
	 * Configures instance of {@link JsonMapper} for JSON serialization.
	 * <p>
	 * The configuration includes -
	 * - Serialization of NULL values
	 * - Formatting of output values
	 * - Required modules
	 *
	 * @return fully configured instance of {@link ObjectMapper}.
	 */
	@Bean(name = { "objectMapper", "jsonMapper" })
	public JsonMapper jsonMapper()
	{
		return createJsonMapper();
	}

	/**
	 * Configures instance of {@link XmlMapper} for XML serialization.
	 * <p>
	 * The configuration includes -
	 * - Serialization of NULL values
	 * - Formatting of output values
	 * - Required modules
	 *
	 * @return fully configured instance of {@link ObjectMapper}.
	 */
	@Bean(name = "xmlMapper")
	public XmlMapper xmlMapper()
	{
		return createXmlMapper();
	}

	/**
	 * Configures message converter for `application/json` and equivalent content types.
	 *
	 * @return instance of message converter for JSON.
	 */
	@Bean(name = "mappingJackson2HttpMessageConverter")
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter()
	{
		return new MappingJackson2HttpMessageConverter(createJsonMapper());
	}

	/**
	 * Configures message converter for `application/xml` and equivalent content types.
	 *
	 * @return instance of message converter for XML.
	 */
	@Bean("mappingJackson2XmlHttpMessageConverter")
	public MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter()
	{
		return new MappingJackson2XmlHttpMessageConverter(createXmlMapper());
	}

	/**
	 * Creates instance of {@link JsonMapper} with application defaults.
	 *
	 * @return fully configured instance of {@link JsonMapper}.
	 */
	private static JsonMapper createJsonMapper()
	{
		// Configuring JSON mapper
		return configureMapper(new JsonMapper());
	}

	/**
	 * Creates instance of {@link XmlMapper} with application defaults.
	 *
	 * @return fully configured instance of {@link XmlMapper}.
	 */
	private static XmlMapper createXmlMapper()
	{
		final XmlMapper xmlMapper = configureMapper(new XmlMapper());

		// Configuring XML mapper
		xmlMapper.registerModule(new JacksonXmlModule()); // Enables serialization to XML
		xmlMapper.registerModule(new JakartaXmlBindAnnotationModule()); // Enables usage of XML-binding annotations for XML and JSON serialization

		return xmlMapper;
	}

	/**
	 * Configures received {@link ObjectMapper} with application defaults.
	 *
	 * @param objectMapper instance to be configured.
	 */
	private static <T extends ObjectMapper> T configureMapper(final T objectMapper)
	{
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // Enables inclusion of only non-fields in serialized content

		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); // Disabling failure generation for unknown properties
		objectMapper.disable(SerializationFeature.INDENT_OUTPUT); // Disabling output formatting
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Disabling generating dates as millisecond values

		objectMapper.registerModules(new Hibernate5JakartaModule()); // Enables support for serializing lazy-loaded hibernate entities
		objectMapper.registerModules(new Jdk8Module()); // Enables support for JDK 8 data types, e.g. Optional
		objectMapper.registerModule(new JavaTimeModule()); // Enables serialization for classes from Java 8 'java.time' package

		return objectMapper;
	}
}
