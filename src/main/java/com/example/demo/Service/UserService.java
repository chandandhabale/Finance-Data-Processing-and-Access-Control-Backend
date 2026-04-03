package com.example.demo.Service;

import java.util.List;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;

public interface UserService {

	 List<User> getAllUsers();
	    User getUserById(Long id);
	    User updateUserRole(Long id, Role role);
	    User toggleUserStatus(Long id);
	    void deleteUser(Long id);
}
