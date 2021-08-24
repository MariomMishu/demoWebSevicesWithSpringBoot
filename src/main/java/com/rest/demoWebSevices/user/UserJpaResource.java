package com.rest.demoWebSevices.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserJpaResource {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private PostRepository postRepo;
	
	//retriveAllUsers
	@GetMapping("/jpa/users")
	public List<User> retriveALlUsers(){
		return repo.findAll();
	}
	
	//retriveUser
	@GetMapping("/jpa/users/{id}")
	public User retriveUser(@PathVariable int id){
		
		Optional<User> u =  repo.findById(id);
		
		if(!u.isPresent()) {
			throw new UserNotFoundException("id - "+id);
		}
		
		
		return u.get();
	}
	
	// Create New User
	/*
	 * @PostMapping("/user") public void createUser(@RequestBody User user) { User
	 * s= service.save(user);
	 * 
	 * }
	 */
		
	@PostMapping("/jpa/user")
	public ResponseEntity<Object> createUser(@Validated @RequestBody User user) {
		User s= repo.save(user);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(s.getBirthDate()).toUri();
		
		return ResponseEntity.created(location).build();
		
	}
	
	//deleteUser
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id){		
		repo.deleteById(id);	
		
	}
	
	//posts
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retriveAllUser(@PathVariable int id){
		
		Optional<User> u =  repo.findById(id);
		
		if(!u.isPresent()) {
			throw new UserNotFoundException("id - "+id);
		}
		
		
		return u.get().getPosts();
	}
	
	@PostMapping("/jpa/user/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post) {
		Optional<User> u =  repo.findById(id);
		
		if(!u.isPresent()) {
			throw new UserNotFoundException("id - "+id);
		}
		User user = u.get();
		post.setUser(user);
		postRepo.save(post);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(post.getId()).toUri();
		
		return ResponseEntity.created(location).build();
		
	}

}
