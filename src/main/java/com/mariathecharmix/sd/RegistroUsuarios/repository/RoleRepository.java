package com.mariathecharmix.sd.RegistroUsuarios.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mariathecharmix.sd.RegistroUsuarios.beans.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{
	
	public Role findByName(String name);

}
