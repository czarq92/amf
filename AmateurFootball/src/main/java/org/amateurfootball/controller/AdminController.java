package org.amateurfootball.controller;

import javax.servlet.http.HttpSession;

import org.amateurfootball.service.TopNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
	
	@Autowired
	private TopNewsService topNewsService;
	
	private String name;
	private String loggedUser;
	
	@RequestMapping(value="/admin")
	public String redirect(Model model, HttpSession session){
		name = "admin";
		loggedUser = (String) session.getAttribute("nameUserLogged");

		model.addAttribute("news", topNewsService.getTopNewsList());
		
		if(loggedUser != null && loggedUser.equals(name) ){
			return "admin";
		}
		
		return "redirect:/index";
	}

}
