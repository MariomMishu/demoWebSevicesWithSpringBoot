package com.rest.demoWebSevices.helloWorld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(path="/hello")
	public String helloWorld() {
		return "hello world";
	}

	@GetMapping(path="/hello-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello world Bean");
	}
	
	@GetMapping(path="/hello/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello world, %s", name));
	}
	
	@GetMapping(path="/hello-word-internationalized")
	public String helloWorldInternationalized(@RequestHeader(name="Accept-Language", required=false) Locale locale) {
		return messageSource.getMessage("good.morning.messages", null, locale);
		//return new HelloWorldBean("Good Morning");
	}
	
	
}
