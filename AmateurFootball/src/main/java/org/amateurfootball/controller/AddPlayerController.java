package org.amateurfootball.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.amateurfootball.model.Player;
import org.amateurfootball.model.Team;
import org.amateurfootball.repository.PlayerRepository;
import org.amateurfootball.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AddPlayerController {

	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private PlayerRepository playerRepository;

	private List<Player> playerList;
	
	@RequestMapping(value = "/addPlayer", method = RequestMethod.GET)
	public String registrationForm(Model model, HttpSession session){
	
		if( session.getAttribute("nameUserLogged") == null ){
			return "redirect:/index";
		}
		
		if( session.getAttribute("idTeamUserLogged") != null ) {
			model.addAttribute("player", new Player());

			return "addPlayer";
		}
		
		session.setAttribute("addTeamInfo", true);
		
		return "redirect:/addTeam";
	}
	
	@RequestMapping(value = "/addPlayer", method = RequestMethod.POST)
	public String registrationSubmit(@Valid @ModelAttribute("player") Player player, 
							BindingResult bindingResult, Model model, 
							HttpSession session){
		boolean teamFinded = false;
		boolean numberExist = false;
		
		session.setAttribute("addTeamInfo", false);
		
		playerList = playerRepository.findAll();
		
		if( bindingResult.hasErrors() ) {
            return "addPlayer";
        }
		
		Long userTeamId = (Long) session.getAttribute("idTeamUserLogged");
		
		if( userTeamId != null ){
			teamFinded = true;
			
			Team team = teamRepository.findOne(userTeamId);
			
			player.setTeam(team);
		} 

		if( teamFinded == true ){
			for ( Player p : playerList ) {
				if( player.getNumber() == p.getNumber() &&
						p.getTeam().getId_team() == userTeamId ){
					numberExist = true;
					model.addAttribute("numberExist", numberExist);
					
					return "addPlayer";
				}
			}
			playerRepository.save(player);
		} 
		
		return "redirect:/coachSite";
	}
}
