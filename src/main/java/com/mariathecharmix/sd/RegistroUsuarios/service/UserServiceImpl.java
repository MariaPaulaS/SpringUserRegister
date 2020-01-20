package com.mariathecharmix.sd.RegistroUsuarios.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mariathecharmix.sd.RegistroUsuarios.beans.User;
import com.mariathecharmix.sd.RegistroUsuarios.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public Iterable<User> getAllUsers() {
	
		return userRepository.findAll();
	}
	
	
	private boolean checkUsernameAvailable(User user) throws Exception {
		
		Optional<User> userFound= userRepository.findByUsername(user.getUsername());
		
		if(userFound.isPresent()) {
			throw new Exception("Nombre de usuario no disponible");
		}
		
		return true;
			
	}
	
	
	private boolean checkPasswordValid(User user) throws Exception {
		
		if(user.getConfirmPassword() == null || user.getConfirmPassword().isEmpty()) {
			throw new Exception("Las verificacion de contraseña es obligatoria");

		}
		
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			
			throw new Exception("Las contraseñas no coinciden");
		}
		
		return true;
		
	}


	@Override
	public User createUser(User user) throws Exception {
		
		if(checkUsernameAvailable(user) && checkPasswordValid(user) ) {
			user = userRepository.save(user);
		}
		
		
		return user;
	}


	@Override
	public User getUserById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return userRepository.findById(id).orElseThrow(()-> new Exception("El usuario que busca no existe") );
	}


	@Override
	public User updateUser(User fromUser) throws Exception {
		// TODO Auto-generated method stub
		User toUser = userRepository.findById(fromUser.getId()).orElseThrow(()-> new Exception ("El usuario no se pudo actualizar"));
		
		mapUser(fromUser, toUser);
		
		userRepository.save(toUser);
		
		return toUser;
		
		
	}
	
	/**
	 * Método que le asigna los atributos de un objeto viejo a un objeto nuevo.
	 * @param from
	 * @param to
	 */
	protected void mapUser(User from, User to) {
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setUsername(from.getUsername());
		to.setEmail(from.getEmail());
		to.setRoles(from.getRoles());
	//	to.setPassword(from.getPassword());
	
	}


	@Override
	public void deleteUser(Long id) throws Exception {
	
		User userToDelete = userRepository.findById(id).orElseThrow(()-> new Exception("El usuario para eliminar no ha sido encontrado"));
		
		userRepository.delete(userToDelete);

	}
	
	
}
