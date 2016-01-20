package org.amateurfootball.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
	private String name;
	private String loggedUser;
	
	@RequestMapping(value="/admin")
	public String redirect(Model model, HttpSession session){
		name = "admin";
		loggedUser = (String) session.getAttribute("nameUserLogged");
		
		if( loggedUser != null && loggedUser.equals(name) ){
			return "admin";
		}
		
		return "redirect:/index";
	}

}
