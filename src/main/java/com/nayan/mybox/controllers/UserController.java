package com.nayan.mybox.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.objenesis.instantiator.basic.NewInstanceInstantiator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.nayan.mybox.models.User;
import com.nayan.mybox.service.MongoUserService;

@Controller	
public class UserController {

	@Autowired
	MongoUserService userService;
	
	Logger userlogs = LoggerFactory.getLogger(UserController.class);

	
	@GetMapping("/signup")
	public String GetSignup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}
	
	@PostMapping("/signin")
	public String AddUser(@ModelAttribute User user) {
		
		userService.addUser(user);
		return "redirect:/dashboard";
	}
}
