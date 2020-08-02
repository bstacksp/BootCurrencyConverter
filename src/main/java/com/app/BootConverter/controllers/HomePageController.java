package com.app.BootConverter.controllers;


import com.app.BootConverter.entities.User;
import com.app.BootConverter.services.CurrencyConverterService;
import com.app.BootConverter.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/home")
public class HomePageController {

	@Autowired
	HistoryService historyService;

	@Autowired
	CurrencyConverterService converterService;

	@RequestMapping(method = RequestMethod.GET)
	public String filter(Model model, HttpServletRequest request){

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/login";
		}
		model.addAttribute("currencies", converterService.getAll());
		model.addAttribute("histories", historyService.getHistoryUser(user));
		return "home";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String converterPost (Model model, HttpServletRequest request) {
		if( request.getParameter("convert") != null && !request.getParameter("sum").equals("")) {
			model.addAttribute("sum", request.getParameter("sum"));
			model.addAttribute("result",
					converterService.convert(
							request.getParameter("sum"),
							request.getParameter("fromCurrency"),
							request.getParameter("toCurrency")));
			model.addAttribute("from",
					converterService.getName(request.getParameter("fromCurrency")));
			model.addAttribute("to",
					converterService.getName(request.getParameter("toCurrency")));
			HttpSession session = request.getSession();
			historyService.saveToHistory((User) session.getAttribute("user"),
					request.getParameter("fromCurrency"),
					request.getParameter("toCurrency"));
		}
		if (request.getParameter("exit") != null) {
			HttpSession session = request.getSession();
			session.removeAttribute("user");
			return "redirect:/";
		}
		return filter(model, request);
	}
}
