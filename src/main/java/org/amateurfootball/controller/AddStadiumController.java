package org.amateurfootball.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.amateurfootball.model.Stadium;
import org.amateurfootball.repository.StadiumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AddStadiumController {
	
	@Autowired
	private StadiumRepository stadiumRepository;
	
	@RequestMapping(value = "/addStadium", method = RequestMethod.GET)
	public String addForm(Model model, HttpSession session){
		
		if(session.getAttribute("nameUserLogged") == null ){
			return "redirect:/index";
		}
		
		model.addAttribute("stadium", new Stadium());
		
		return "addStadium";
	}
	
	@RequestMapping(value = "/addStadium", method = RequestMethod.POST)
	public String addnSubmit(@Valid @ModelAttribute("stadium") Stadium stadium, 
							BindingResult bindingResult, Model model, 
							HttpSession session){

		if(bindingResult.hasErrors()) {
            return "addStadium";
        }
		
		stadiumRepository.save(stadium);
		
		return "redirect:/addTeam";
	}
}
