package com.mariathecharmix.sd.RegistroUsuarios.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mariathecharmix.sd.RegistroUsuario.dto.ChangePasswordForm;
import com.mariathecharmix.sd.RegistroUsuario.exceptions.CustomeFieldValidationException;
import com.mariathecharmix.sd.RegistroUsuario.exceptions.UsernameOrIdNotFoundException;
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

	@GetMapping({"/", "/login"})
	public String index() {
		return "index";

	}

	@GetMapping("/userForm")
	public String userForm(Model model) {
		model.addAttribute("userForm", new User());
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("listTab", "active");
		return "user-form/user-view";
	}

	@PostMapping("/userForm")
	public String createUser(@Valid @ModelAttribute("userForm") User user, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			model.addAttribute("userForm", user);
			model.addAttribute("formTab", "active");
		}

		else {

			try {
				userService.createUser(user);
				model.addAttribute("userForm", new User());
				model.addAttribute("listTab", "active");
			}
			
			 catch (CustomeFieldValidationException e) {
					result.rejectValue(e.getFieldName(), null, e.getMessage());
					model.addAttribute("userForm", user);
					model.addAttribute("formTab", "active");
					model.addAttribute("userList", userService.getAllUsers());
					model.addAttribute("roles", roleRepository.findAll());

				}

			
			 catch (Exception e) {
					// TODO Auto-generated catch block
					model.addAttribute("formErrorMessage", e.getMessage());
					model.addAttribute("userForm", user);
					model.addAttribute("formTab", "active");
					model.addAttribute("userList", userService.getAllUsers());
					model.addAttribute("roles", roleRepository.findAll());

				}
		}

		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles", roleRepository.findAll());

		return "user-form/user-view";

	}

	@GetMapping("/editUser/{id}")
	public String getEditUserForm(Model model, @PathVariable(name = "id") Long id) throws Exception {

		User userToEdit = userService.getUserById(id);

		model.addAttribute("userForm", userToEdit);
		model.addAttribute("roles", roleRepository.findAll());
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("formTab", "active");
		model.addAttribute("editMode", "true");
		model.addAttribute("passwordForm", new ChangePasswordForm(id));

		return "user-form/user-view";

	}

	@PostMapping("/editUser")
	public String postEditUserForm(@Valid @ModelAttribute("userForm") User user, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute("userForm", user);
			model.addAttribute("formTab", "active");
			model.addAttribute("editMode", "true");
			model.addAttribute("passwordForm", new ChangePasswordForm(user.getId()));
		} else {
			try {
				userService.updateUser(user);
				model.addAttribute("userForm", new User());
				model.addAttribute("listTab", "active");
			} catch (Exception e) {
				model.addAttribute("formErrorMessage", e.getMessage());
				model.addAttribute("userForm", user);
				model.addAttribute("formTab", "active");
				model.addAttribute("userList", userService.getAllUsers());
				model.addAttribute("roles", roleRepository.findAll());
				model.addAttribute("editMode", "true");
				model.addAttribute("passwordForm", new ChangePasswordForm(user.getId()));

			}
		}

		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles", roleRepository.findAll());
		return "user-form/user-view";

	}

	@GetMapping("/userForm/cancel")
	public String cancelEditUser(ModelMap model) {
		return "redirect:/userForm";
	}

	@GetMapping("/deleteUser/{id}")
	public String deleteUser(Model model, @PathVariable(name = "id") Long id) throws UsernameOrIdNotFoundException {


		try {

			userService.deleteUser(id);

		} catch (UsernameOrIdNotFoundException e) {
			model.addAttribute("listErrorMessage", e.getMessage());
		}

		return userForm(model);
	}

	@PostMapping("/editUser/changePassword")
	public ResponseEntity postEditUseChangePassword(@Valid @RequestBody ChangePasswordForm form, Errors errors) {

		try {

			if (errors.hasErrors()) {
				// Guarda todos los errores en un string
				String result = errors.getAllErrors().stream().map(x -> x.getDefaultMessage())
						.collect(Collectors.joining(""));

				throw new Exception(result);

			}

			userService.changePassword(form);
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

		return ResponseEntity.ok("success");

	}
}
