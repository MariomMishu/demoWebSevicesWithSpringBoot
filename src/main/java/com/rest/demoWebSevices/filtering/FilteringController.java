package com.rest.demoWebSevices.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
@RestController
public class FilteringController {
	
	@GetMapping("/filtering")
	public SomeBean retriveSomeBean() {	
		SomeBean someBean = new SomeBean("v1","v2","v3");	
 		return someBean;
		
	}
	
	//field1.field2
	@GetMapping("/dynamic-filtering")
	public MappingJacksonValue dynamicRetriveSomeBean() {	
		SomeBean someBean = new SomeBean("v1","v2","v3");
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(someBean);
		mapping.setFilters(filters);
 		return mapping;
			
		}
	
	@GetMapping("/filtering-list")
	public List<SomeBean> retriveListOfSomeBeans() {		
		return Arrays.asList(new SomeBean("v1","v2","v3"),new SomeBean("v4","v5","v6"));
		
	}
	
	//field2, field3
	@GetMapping("/dynamic-filtering-list")
	public MappingJacksonValue dynamicreRriveListOfSomeBeans() {	
		List<SomeBean> list =Arrays.asList(new SomeBean("v1","v2","v3"),new SomeBean("v4","v5","v6"));
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2","field3");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(list);
		mapping.setFilters(filters);
		return mapping;
		
	}


}
