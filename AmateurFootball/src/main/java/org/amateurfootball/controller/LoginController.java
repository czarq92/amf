package org.amateurfootball.controller;

import java.util.List;
import javax.servlet.http.HttpSession;

import org.amateurfootball.model.Coach;
import org.amateurfootball.repository.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	@Autowired
	private CoachRepository coachRepository;
	
	private List<Coach> list;
	private boolean coachExist;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm(Model model){
		model.addAttribute("user", new Coach()); //return to model object of new Coach() using name 'user'
		
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginSubmit(@ModelAttribute Coach coach, Model model, HttpSession session){
		coachExist = false;
		
		model.addAttribute("user", coach);

		list = coachRepository.findAll();
		
		for (Coach c : list) {
			if( (c.getLogin().equals(coach.getLogin())) 
				&& (c.getPassword().equals(coach.getPassword())) ){
				
					coachExist = true;
					
					Long findedId_coach = c.getId_coach();

					session.setAttribute("nameUserLogged", c.getLogin()); //save user login in session variable nameUserLogged
					session.setAttribute("idUserLogged", findedId_coach);
					
					if(c.getId_coach() == 0){
						return "admin";
					}
			}
		}
		
		if(coachExist == false){
			model.addAttribute("badLogin", true);
			return "login";
		}
		
		return "redirect:/coachSite";
	}
	
}
