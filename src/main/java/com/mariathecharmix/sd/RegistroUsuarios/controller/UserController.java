package com.mariathecharmix.sd.RegistroUsuarios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mariathecharmix.sd.RegistroUsuarios.beans.User;
import com.mariathecharmix.sd.RegistroUsuarios.repository.RoleRepository;
import com.mariathecharmix.sd.RegistroUsuarios.repository.UserRepository;
import com.mariathecharmix.sd.RegistroUsuarios.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserService userService;

	
	@GetMapping("/")
	public String index() {
		return "index";
		
	}
	
	@GetMapping("/userForm")
	public String userForm(Model model){
		model.addAttribute("userForm", new User());
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("listTab", "active");
		return "user-form/user-view";
	}
}
