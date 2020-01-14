package com.mariathecharmix.sd.RegistroUsuarios;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.mariathecharmix.sd.controller.RegistroUsuariosApplication;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RegistroUsuariosApplication.class);
	}

}
