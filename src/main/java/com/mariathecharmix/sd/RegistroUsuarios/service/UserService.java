package com.mariathecharmix.sd.RegistroUsuarios.service;

import org.springframework.stereotype.Service;

import com.mariathecharmix.sd.RegistroUsuarios.beans.User;


public interface UserService {
	
	public Iterable<User> getAllUsers();

}
