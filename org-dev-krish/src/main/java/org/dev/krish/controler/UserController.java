package org.dev.krish.controler;

import java.util.List;

import org.dev.krish.entity.User;
import org.dev.krish.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value="/user")
public class UserController{
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/all")
	public List<User> listUsers(){
		return (List<User>) userRepository.findAll();
	}
	@PostMapping(value="/load")
	public List<User> persist(@RequestBody User user) {
		userRepository.save(user);
		return userRepository.findAll();
	}
}
