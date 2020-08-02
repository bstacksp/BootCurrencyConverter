package com.app.BootConverter.controllers;

import com.app.BootConverter.entities.User;
import com.app.BootConverter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/login")
public class LoginPageController {

	@Autowired
	UserService userService;

	@RequestMapping( method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String postLogin(User user, HttpServletRequest request) {
		if (userService.login(user.getLogin(), user.getPassword())) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			return "redirect:/home";
		}
		return "login";
	}
}