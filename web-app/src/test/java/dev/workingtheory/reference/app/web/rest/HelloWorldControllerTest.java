package dev.workingtheory.reference.app.web.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static dev.workingtheory.reference.app.support.TestHelpers.IS_NOT_BLANK_MATCHER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = HelloWorldController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class HelloWorldControllerTest
{
	@Autowired
	private MockMvc mockMvc;

	@Test
	void sayHelloInJson() throws Exception
	{
		this.mockMvc.perform(get("/greet/Neo").accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.name").value("Neo"))
					.andExpect(jsonPath("$.timestamp").isNotEmpty());
	}

	@Test
	void sayHelloInXml() throws Exception
	{
		this.mockMvc.perform(get("/greet/Neo").accept(MediaType.APPLICATION_XML))
					.andExpect(status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
					.andExpect(xpath("Greeting/name").string("Neo"))
					.andExpect(xpath("Greeting/timestamp").string(IS_NOT_BLANK_MATCHER));
	}
}