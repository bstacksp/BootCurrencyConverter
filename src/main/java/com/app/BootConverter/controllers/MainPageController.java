package com.app.BootConverter.controllers;

import com.app.BootConverter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class MainPageController {

	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String main(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return "main";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String exit(HttpServletRequest request){
		userService.deleteUser(Long.parseLong(request.getParameter("delete")));
		return "redirect:/";
	}
}
