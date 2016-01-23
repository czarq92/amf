package org.amateurfootball.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.amateurfootball.model.Coach;
import org.amateurfootball.model.Team;
import org.amateurfootball.repository.CoachRepository;
import org.amateurfootball.repository.StadiumRepository;
import org.amateurfootball.repository.TeamRepository;
import org.amateurfootball.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EditTeamController {
	
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private StadiumRepository stadiumRepository;
	private boolean nameExist;
	
	@RequestMapping(value = "/editTeam/{idTeam}", method = RequestMethod.GET)
	public String editForm(@PathVariable("idTeam") long idTeam, Model model, HttpSession session){
		
		if( session.getAttribute("nameUserLogged") == null ){
			return "redirect:/index";
		}
		
		model.addAttribute("team", teamRepository.findOne(idTeam));
		model.addAttribute("stadium", stadiumRepository.findAll());
		
		return "editTeam";
	}
	
	@RequestMapping(value = "/editTeam", method = RequestMethod.POST)
	public String editSubmit(@Valid @ModelAttribute("team") Team team, 
							BindingResult bindingResult, Model model, 
							HttpSession session){
		nameExist = false;
		
		session.setAttribute("addTeamInfo", false);
		model.addAttribute("stadium", stadiumRepository.findAll());
		
		Team teamExist = teamRepository.findTeamByName(team.getName());

		Long idTmp = (Long) session.getAttribute("idTeamUserLogged");
		
		if(teamExist != null){
			if(teamExist.getCoach().getId_coach() == idTmp){
				
				return "redirect:/coachSite";
			}

			nameExist = true;
			model.addAttribute("nameExist", nameExist);
			
			return "editTeam";
		}
		
		if(bindingResult.hasErrors()) {
            return "editTeam";
        }
		
		teamRepository.save(team);

		return "redirect:/coachSite";
	}
}
