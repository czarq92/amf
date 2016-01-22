package org.amateurfootball.controller;

import javax.servlet.http.HttpSession;

import org.amateurfootball.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DeletePlayerController {
	
	@Autowired
	private TeamService teamService;
	
	@RequestMapping(value = "/delPlayer/{idPlayer}", method = RequestMethod.GET)
	public String deletePlayer(@PathVariable("idPlayer") int idPlayer, Model model, HttpSession session){
		
		if( session.getAttribute("nameUserLogged") == null ){
			return "redirect:/index";
		}
		
		Long teamUserId = (Long) session.getAttribute("idTeamUserLogged");
		
		teamService.deletePlayerFromTeamPlayerList(teamUserId, idPlayer);
		
		return "redirect:/playerList";
	}
}
