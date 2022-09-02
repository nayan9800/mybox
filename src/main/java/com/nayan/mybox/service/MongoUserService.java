package com.nayan.mybox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nayan.mybox.models.User;
import com.nayan.mybox.models.UserRepository;

@Service
public class MongoUserService implements UserDetailsService{

	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User u = userRepository.findByUsername(username);
		
		if (u == null) {
			throw new UsernameNotFoundException(username+" Not Found");
		}
		return u;
	}
	
	
	public void addUser(User users) {
		userRepository.save(users);
	}
	
	
	
	
}
