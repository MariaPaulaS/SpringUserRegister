package com.mariathecharmix.sd.RegistroUsuarios.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mariathecharmix.sd.RegistroUsuarios.beans.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

}