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
		final JsonMapper objectMapper = new JsonMapper();

		// Configuring JSON mapper
		configureMapper(objectMapper);

		return objectMapper;
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
		final XmlMapper xmlMapper = new XmlMapper();

		// Configuring XML mapper
		configureMapper(xmlMapper);

		return xmlMapper;
	}

	/**
	 * Provides message converter for JSON.
	 *
	 * @return instance of message converter for JSON.
	 */
	@Bean(name = "mappingJackson2HttpMessageConverter")
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter()
	{
		final JsonMapper jsonMapper = new JsonMapper();

		// Configuring JSON mapper
		configureMapper(jsonMapper);

		return new MappingJackson2HttpMessageConverter(jsonMapper);
	}

	/**
	 * Provides message converter for XML.
	 *
	 * @return instance of message converter for XML.
	 */
	@Bean("mappingJackson2XmlHttpMessageConverter")
	public MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter()
	{
		final XmlMapper xmlMapper = new XmlMapper();

		// Configuring XML mapper
		configureMapper(xmlMapper);

		return new MappingJackson2XmlHttpMessageConverter(xmlMapper);
	}

	/**
	 * Configures received {@link ObjectMapper} with application defaults.
	 *
	 * @param objectMapper instance to be configured.
	 */
	private static void configureMapper(final ObjectMapper objectMapper)
	{
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // Enables inclusion of only non-fields in serialized content

		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); // Disabling failure generation for unknown properties
		objectMapper.disable(SerializationFeature.INDENT_OUTPUT); // Disabling output formatting
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Disabling generating dates as millisecond values

		objectMapper.registerModule(new JacksonXmlModule()); // Enables serialization to XML
		objectMapper.registerModule(new JakartaXmlBindAnnotationModule()); // Enables usage of XML binding annotations for XML and JSON serialization

		objectMapper.registerModules(new Hibernate5JakartaModule()); // Enables support for serializing lazy-loaded hibernate entities
		objectMapper.registerModules(new Jdk8Module()); // Enables support for JDK 8 data types e.g. Optional
		objectMapper.registerModule(new JavaTimeModule()); // Enables serialization for classes from Java 8 'java.time' package
	}
}