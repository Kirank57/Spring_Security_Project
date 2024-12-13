package com.kiran.serviceimp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kiran.model.User;
import com.kiran.model.UserPrincipal;
import com.kiran.repository.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo repo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	User user= repo.findByUsername(username);
	
	if (user==null) {
		System.out.println("User 404");
		throw new UsernameNotFoundException("User 404");
	}
		 return new UserPrincipal(user);
	}
	
	public List<User> getUser(){
		return repo.findAll();
	}


	public User saveuser(User user) {
		 return repo.save(user);
	}

	public boolean findbyemail(String email) {
		 if(repo.findByEmail(email)==null) {
			 return true;
		 }
		 else {
			 return false;
		 }
	}

	public void deleteById(int id) {
		repo.deleteById(id);
	}
	

}
