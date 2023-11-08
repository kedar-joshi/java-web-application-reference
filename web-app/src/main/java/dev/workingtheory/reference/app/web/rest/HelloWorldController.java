package dev.workingtheory.reference.app.web.rest;

import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class HelloWorldController
{
	private static final Logger logger = LogManager.getLogger(HelloWorldController.class);

	@GetMapping(value = "/greet/{name}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Greeting> sayHello(final @PathVariable("name") String name)
	{
		return ResponseEntity.ok(new Greeting(name, new Timestamp(System.currentTimeMillis())));
	}

	/**
	 * A sample record to hold the greeting message.
	 *
	 * @param name      The name of the person to greet.
	 * @param timestamp The timestamp of the greeting.
	 */
	public record Greeting(String name, Timestamp timestamp)
	{

	}

}
