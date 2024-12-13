package com.kiran.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.kiran.model.User;
import com.kiran.serviceimp.MyUserDetailsService;

@Controller
public class UserController {
	@Autowired
	MyUserDetailsService ser;


	@Autowired
	private PasswordEncoder passwordEncoder;


	@GetMapping("/admin")
	public String adminPage(Model model) {
		List<User> user=ser.getUser();
		model.addAttribute("user", user);
		return "adminpage";
	}


	@GetMapping("/user")
	public String userPage(Model model) {
		return "userpage";
	}

	@GetMapping("/")
	public String Homepage() {
		return "home"; 
	}


	@GetMapping("/login")
	public String loginPage() {
		return "login"; 
	}


	@GetMapping("/delete/{id}")
	public String  deleteById(@PathVariable int id) {
		ser.deleteById(id);
		return "login";
	}


	@GetMapping("/register")
	public String Register() {
		return "register";
	}

	@PostMapping("/submit")
	public String AddUser(@ModelAttribute User user, Model model) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		user.setUsername(user.getUsername());
		user.setEmail(user.getEmail());
		user.setAge(user.getAge());
		user.setGender(user.getGender());
		user.setRole("USER");
		boolean status=ser.findbyemail(user.getEmail());
		if(status) {
			ser.saveuser(user);
		}
		else {
			System.out.println("User Already Exist");
			model.addAttribute("error", "User Alerady Exist");
		}
		return "/register";
	}




}
