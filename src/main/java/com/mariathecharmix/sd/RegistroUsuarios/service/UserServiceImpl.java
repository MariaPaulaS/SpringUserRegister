package com.mariathecharmix.sd.RegistroUsuarios.service;

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

}
