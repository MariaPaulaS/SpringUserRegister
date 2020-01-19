package com.mariathecharmix.sd.RegistroUsuarios.service;


import com.mariathecharmix.sd.RegistroUsuarios.beans.User;


public interface UserService {
	
	public Iterable<User> getAllUsers();

	public User createUser(User user) throws Exception;
	
	public User getUserById(Long id) throws Exception;
	
	public User updateUser(User formUser) throws Exception;

}
