package org.amateurfootball.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.amateurfootball.model.Coach;
import org.amateurfootball.repository.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

	@Autowired
	private CoachRepository coachRepository;
	private List<Coach> list;
	private boolean loginExist;
	
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registrationForm(Model model){
		
		model.addAttribute("user", new Coach());
		
		return "registration";
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registrationSubmit(@Valid @ModelAttribute("user") Coach coach, 
							BindingResult bindingResult, Model model, 
							HttpSession session, @RequestParam("passwordRepeat") String passRepeat){
		loginExist = false;

		list = coachRepository.findAll();
		
		for (Coach c : list) {
			if( (c.getLogin().equals(coach.getLogin())) ){
				loginExist = true;

				model.addAttribute("loginExist", loginExist);
				
				return "registration";
			}
		}
		
		if(!coach.getPassword().equals(passRepeat)){
			model.addAttribute("differentPasswords", true);
			
			return "registration";
		}
		
		if(bindingResult.hasErrors()) {
            return "registration";
        }
		
		if(loginExist == false){
			coachRepository.save(coach);
			
			model.addAttribute("isLogged", true);
			session.setAttribute("nameUserLogged", coach.getLogin());
		}
			
		return "redirect:/coachSite";
	}
		
}
