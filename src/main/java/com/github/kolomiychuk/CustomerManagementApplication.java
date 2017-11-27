package com.github.kolomiychuk;

import com.github.kolomiychuk.config.CustomUserDetails;
import com.github.kolomiychuk.entities.Role;
import com.github.kolomiychuk.entities.User;
import com.github.kolomiychuk.repositories.UserRepository;
import com.github.kolomiychuk.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;


@SpringBootApplication
public class CustomerManagementApplication {

	public static final String USER = "user";
	public static final String ADMIN = "admin";
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(CustomerManagementApplication.class, args);
	}

	/**
	 * Password grants are switched on by injecting an AuthenticationManager.
	 * Here, we setup the builder so that the userDetailsService is the one we coded.
	 * @param builder
	 * @param repository
	 * @throws Exception
     */
	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository repository, UserService service) throws Exception {
		//Setup a default user if db is empty
		if (repository.count()==0)
			service.save(new User(USER, USER, Arrays.asList(new Role("USER"))));
			service.save(new User(ADMIN, ADMIN, Arrays.asList(new Role("ADMIN"), new Role("USER"))));
		builder.userDetailsService(userDetailsService(repository)).passwordEncoder(passwordEncoder);
	}

	/**
	 * We return an istance of our CustomUserDetails.
	 * @param repository
	 * @return
     */
	private UserDetailsService userDetailsService(final UserRepository repository) {
		return username -> new CustomUserDetails(repository.findByUsername(username));
	}

}
