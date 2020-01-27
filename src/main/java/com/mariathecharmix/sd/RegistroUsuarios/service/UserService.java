package com.mariathecharmix.sd.RegistroUsuarios.service;


import com.mariathecharmix.sd.RegistroUsuario.dto.ChangePasswordForm;
import com.mariathecharmix.sd.RegistroUsuario.exceptions.UsernameOrIdNotFoundException;
import com.mariathecharmix.sd.RegistroUsuarios.beans.User;


public interface UserService {
	
	public Iterable<User> getAllUsers();

	public User createUser(User user) throws Exception;
	
	public User getUserById(Long id) throws Exception;
	
	public User updateUser(User formUser) throws Exception;
	
	public void deleteUser(Long id) throws UsernameOrIdNotFoundException;
	
	public User changePassword(ChangePasswordForm form) throws Exception;

}
