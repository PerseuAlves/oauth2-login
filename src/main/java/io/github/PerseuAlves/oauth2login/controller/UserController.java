package io.github.PerseuAlves.oauth2login.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.PerseuAlves.oauth2login.model.UserLogin;
import io.github.PerseuAlves.oauth2login.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService service;

	@GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
		Optional<UserLogin> user = service.findById(principal.getAttribute("name"));
		
		if(user.isPresent()) {
			return Collections.singletonMap("name", principal.getAttribute("name"));
		} else {
			service.save(new UserLogin(principal.getAttribute("name")));
			
			return Collections.singletonMap("name", principal.getAttribute("name"));
		}
    }
	
	@GetMapping("/users")
    public Map<String, Object> users(@AuthenticationPrincipal OAuth2User principal) {
		List<UserLogin> users = service.findAll();
		
		return Collections.singletonMap("users", users.get(0).getName());
    }
}
