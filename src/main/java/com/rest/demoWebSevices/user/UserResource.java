package com.rest.demoWebSevices.user;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat.Resource;

//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {
	
	@Autowired
	private UserDaoService service;
	
	//retriveAllUsers
	@GetMapping("/users")
	public List<User> retriveALlUsers(){
		return service.findAll();
	}
	
	//retriveUser
	@GetMapping("/users/{id}")
	public User retriveUser(@PathVariable int id){
		
		User u =  service.findOne(id);
		
		if(u==null) {
			throw new UserNotFoundException("id - "+id);
		}
		
		
		return u;
	}
	
	// Create New User
	/*
	 * @PostMapping("/user") public void createUser(@RequestBody User user) { User
	 * s= service.save(user);
	 * 
	 * }
	 */
		
	@PostMapping("/user")
	public ResponseEntity<Object> createUser(@Validated @RequestBody User user) {
		User s= service.save(user);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(s.getBirthDate()).toUri();
		
		return ResponseEntity.created(location).build();
		
	}
	
	

	//deleteUser
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id){
		
		User u =  service.deleteById(id);
		
		if(u==null) {
			throw new UserNotFoundException("id - "+id);
		}
	}
		

}
