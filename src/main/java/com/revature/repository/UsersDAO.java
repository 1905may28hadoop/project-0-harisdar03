package com.revature.repository;

import java.util.List;

import com.revature.model.Users;


public interface UsersDAO {

	Users getUsers(long id);
	
	Users getUsers(String username);
	
	
	

	
	boolean createUsers(Users user);
	
	boolean updateUsers(Users user);
	
	List<Users> getUsers();
}
