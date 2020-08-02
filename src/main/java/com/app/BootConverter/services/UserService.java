package com.app.BootConverter.services;

import com.app.BootConverter.entities.User;

import java.util.List;

public interface UserService {

	Boolean login(String login, String password);

	List<User> getAllUsers();

	void addUser(User user);

	void deleteUser(Long id);
}
