package org.amateurfootball.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.amateurfootball.model.Coach;
import org.amateurfootball.model.Player;
import org.amateurfootball.repository.CoachRepository;
import org.amateurfootball.repository.PlayerRepository;
import org.amateurfootball.service.TopNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CoachSiteController {

	@Autowired
	private TopNewsService topNewsService;
	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	private CoachRepository coachRepository;
	
	private List<Player> coachPlayerList;
	
	public void initialize(HttpSession session){
		List<Player> tmpList = playerRepository.findAll();
		
		coachPlayerList = new ArrayList<>();
		
		String tmpLogin = (String) session.getAttribute("nameUserLogged");
		
		Coach loggedCoach = coachRepository.findCoachByLogin(tmpLogin);
		
		if( loggedCoach.getTeam() != null ){
			Long teamId = loggedCoach.getTeam().getId_team();
			
			session.setAttribute("idTeamUserLogged", teamId);
		}
	
		Long id_teamUserLogged = (Long) session.getAttribute("idTeamUserLogged");
		
		if( id_teamUserLogged != null ){
			for ( Player p : tmpList ) {
				if(p.getTeam() != null){
					if( p.getTeam().getId_team() == id_teamUserLogged ){
						coachPlayerList.add(p);
					}
				}
			}
			
			Collections.sort(coachPlayerList);
			
			session.setAttribute("usedPlayerIndexEdit", 0);
			session.setAttribute("coachPlayerList", coachPlayerList);
		}
	}
	
	@RequestMapping(value="/coachSite")
	public String returnAndRedirect(Model model, HttpSession session){
		model.addAttribute("news", topNewsService.getTopNewsList());
		
		if( session.getAttribute("nameUserLogged") == null ){
			return "redirect:/index";
		}
		
		initialize(session);
		
		return "coachSite";
	}
	
}
