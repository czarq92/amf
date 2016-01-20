package org.amateurfootball.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.amateurfootball.model.Match;
import org.amateurfootball.repository.MatchRepository;
import org.amateurfootball.repository.MatchStatisticsRepository;
import org.amateurfootball.repository.StadiumRepository;
import org.amateurfootball.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static java.lang.Math.toIntExact;

@Controller
 public class MatchSiteController {

	@Autowired
	private MatchRepository matchRepository;
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private StadiumRepository stadiumRepository;
	@Autowired
	private MatchStatisticsRepository matchStatRepository;
	
	private List<Match> matchList;
	
	@RequestMapping(value = "/matchSite")
	public String goToPage(Model model, HttpSession session){
		
		if( session.getAttribute("nameUserLogged") == null ){
			return "redirect:/index";
		}
		
		List<Match> tmpMatchList = new ArrayList<>();
		tmpMatchList = matchRepository.findAll();

		matchList = new ArrayList<>();
		
		Long idUserTeam = (Long) session.getAttribute("idTeamUserLogged");
		
		if(idUserTeam == null){
			model.addAttribute("emptyListInfo", true);
			return "matchSite";
		}
		
		int intIdUserTeam = toIntExact(idUserTeam);
		
		for (Match match : tmpMatchList) {
			if(match.getFirst_team_id() == intIdUserTeam || match.getSecond_team_id() == intIdUserTeam ){
				matchList.add(match);
			}
		}
		
		if(!matchList.isEmpty()){
			Collections.reverse(matchList);

			model.addAttribute("matches", matchList);
			model.addAttribute("teams", teamRepository.findAll());
			model.addAttribute("stadiums", stadiumRepository.findAll());
			model.addAttribute("matchStats", matchStatRepository.findAll());
			
			return "matchSite";
			
		} else {
			model.addAttribute("emptyListInfo", true);
			return "matchSite";
		}
	}
}
