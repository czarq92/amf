package org.amateurfootball.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
//import static java.lang.Math.toIntExact;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AddTeamController {

	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private CoachRepository coachRepository;
	@Autowired
	private StadiumRepository stadiumRepository;
	@Autowired
	private TeamService teamService;
	
	private String loggedCoach;
	private boolean nameExist;
	private boolean trainAlready;

	
	@RequestMapping(value = "/addTeam", method = RequestMethod.GET)
	public String registrationForm(Model model, HttpSession session){
		
		if( session.getAttribute("nameUserLogged") == null ){
			return "redirect:/index";
		}
		
		model.addAttribute("team", new Team());
		model.addAttribute("stadium", stadiumRepository.findAll());
		
		return "addTeam";
	}
	
	@RequestMapping(value = "/addTeam", method = RequestMethod.POST)
	public String registrationSubmit(@Valid @ModelAttribute("team") Team team, 
							BindingResult bindingResult, Model model, 
							HttpSession session){
		nameExist = false;
		
		session.setAttribute("addTeamInfo", false);
		model.addAttribute("stadium", stadiumRepository.findAll());
		
		Team teamExist = teamRepository.findTeamByName(team.getName());

		if(teamExist != null){
			nameExist = true;
			model.addAttribute("nameExist", nameExist);
			
			return "addTeam";
		}
		
		if(bindingResult.hasErrors()) {
            return "addTeam";
        }
		
		if(nameExist == false){
			loggedCoach = (String) session.getAttribute("nameUserLogged");
			
			Coach c = coachRepository.findCoachByLogin(loggedCoach);
					
			if(c.getTeam() != null){
				trainAlready = true;
				
				model.addAttribute("trainAlready", trainAlready);
				
				return "addTeam";
			} else {
				int rankingNumber = teamService.findLastTeamInRanking();

				team.setCoach(c);
				team.setRanked(rankingNumber);
				
				teamRepository.save(team);
			}
		}
		
		return "redirect:/coachSite";
	}
}
