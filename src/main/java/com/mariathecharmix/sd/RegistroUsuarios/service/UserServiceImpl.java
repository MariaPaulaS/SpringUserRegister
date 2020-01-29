package com.mariathecharmix.sd.RegistroUsuarios.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mariathecharmix.sd.RegistroUsuario.dto.ChangePasswordForm;
import com.mariathecharmix.sd.RegistroUsuario.exceptions.CustomeFieldValidationException;
import com.mariathecharmix.sd.RegistroUsuario.exceptions.UsernameOrIdNotFoundException;
import com.mariathecharmix.sd.RegistroUsuarios.beans.User;
import com.mariathecharmix.sd.RegistroUsuarios.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Iterable<User> getAllUsers() {

		return userRepository.findAll();
	}

	private boolean checkUsernameAvailable(User user) throws Exception {

		Optional<User> userFound = userRepository.findByUsername(user.getUsername());

		if (userFound.isPresent()) {
			throw new CustomeFieldValidationException("Nombre de usuario no disponible", "username");
		}

		return true;

	}

	private boolean checkPasswordValid(User user) throws Exception {

		if (user.getConfirmPassword() == null || user.getConfirmPassword().isEmpty()) {
			throw new CustomeFieldValidationException("Las verificacion de contraseña es obligatoria", "confirmPassword");

		}

		if (!user.getPassword().equals(user.getConfirmPassword())) {

			throw new CustomeFieldValidationException("Las contraseñas no coinciden", "password");
		}

		return true;

	}

	@Override
	public User createUser(User user) throws Exception {

		if (checkUsernameAvailable(user) && checkPasswordValid(user)) {

			String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
			user.setPassword(encodePassword);
			user = userRepository.save(user);
		}

		return user;
	}

	@Override
	public User getUserById(Long id) throws UsernameOrIdNotFoundException {
		return userRepository.findById(id)
				.orElseThrow(() -> new UsernameOrIdNotFoundException("El Id del usuario no existe."));
	}

	@Override
	public User updateUser(User fromUser) throws Exception {
		// TODO Auto-generated method stub
		User toUser = userRepository.findById(fromUser.getId())
				.orElseThrow(() -> new Exception("El usuario no se pudo actualizar."));

		mapUser(fromUser, toUser);

		userRepository.save(toUser);

		return toUser;

	}

	/**
	 * Método que le asigna los atributos de un objeto viejo a un objeto nuevo.
	 * 
	 * @param from
	 * @param to
	 */
	protected void mapUser(User from, User to) {
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setUsername(from.getUsername());
		to.setEmail(from.getEmail());
		to.setRoles(from.getRoles());
		// to.setPassword(from.getPassword());

	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public void deleteUser(Long id) throws UsernameOrIdNotFoundException {
		User user = getUserById(id);
		userRepository.delete(user);
	}

	public User changePassword(ChangePasswordForm form) throws Exception {
		User storedUser = userRepository.findById(form.getId())
				.orElseThrow(() -> new Exception("UsernotFound in ChangePassword -" + this.getClass().getName()));

		if (!isLoggedUserADMIN() && !form.getCurrentPassword().equals(storedUser.getPassword())) {
			throw new Exception("Contraseña actual incorrecta");
		}

		if (form.getCurrentPassword().equals(form.getNewPassword())) {
			throw new Exception("La contraseña nueva debe ser diferente de la actual");
		}

		if (!form.getNewPassword().equals(form.getConfirmPassword())) {
			throw new Exception("La contraseña nueva y la confirmacion no son iguales.");
		}

		String encodePassword = bCryptPasswordEncoder.encode(form.getNewPassword());
		storedUser.setPassword(encodePassword);
		return userRepository.save(storedUser);
	}

	private boolean isLoggedUserADMIN() {
		// Obtener el usuario logeado
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		UserDetails loggedUser = null;
		Object roles = null;

		// Verificar que ese objeto traido de sesion es el usuario
		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;

			roles = loggedUser.getAuthorities().stream().filter(x -> "ROLE_ADMIN".equals(x.getAuthority())).findFirst()
					.orElse(null);
		}
		return roles != null ? true : false;
	}
	
	
	private User getLoggedUser() throws Exception {
		//Obtener el usuario logeado
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserDetails loggedUser = null;

		//Verificar que ese objeto traido de sesion es el usuario
		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;
		}
		
		User myUser = userRepository
				.findByUsername(loggedUser.getUsername()).orElseThrow(() -> new Exception("Problemas obteniendo usuario de sesion."));
		
		return myUser;
	}
	

}
