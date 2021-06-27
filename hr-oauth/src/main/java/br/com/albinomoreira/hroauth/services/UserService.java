package br.com.albinomoreira.hroauth.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.albinomoreira.hroauth.entities.User;
import br.com.albinomoreira.hroauth.feignclients.UserFeignClient;

@Service
public class UserService implements UserDetailsService{
	
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserFeignClient userFeignClient;
	
	public User findByEmail(String email) {
		User user = userFeignClient.findByEmail(email).getBody();
		
		if(user == null) {
			logger.error("Email não encontrado: " + email);
			throw new IllegalArgumentException("Email não encontrado");
		}
		logger.error("Email encontrado: " + email);
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userFeignClient.findByEmail(username).getBody();
		
		if(user == null) {
			logger.error("Email não encontrado: " + username);
			throw new UsernameNotFoundException("Email não encontrado");
		}
		logger.error("Email encontrado: " + username);
		return user;
	}

}
