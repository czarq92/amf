package org.amateurfootball.controller;

import org.amateurfootball.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TeamListController {

	@Autowired
	private TeamRepository teamRepository;
	
	@RequestMapping(value = "/teamList")
	public String teamListReturn(Model model){
		model.addAttribute("teamList", teamRepository.findAll());
		
		return "teamList";
	}
}
