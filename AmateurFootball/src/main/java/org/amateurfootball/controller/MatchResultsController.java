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

@Controller
public class MatchResultsController {
	
	@Autowired
	private MatchRepository matchRepository;
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private StadiumRepository stadiumRepository;
	@Autowired
	private MatchStatisticsRepository matchStatRepository;
	
	private List<Match> matchList;
	
	@RequestMapping(value = "/matchResults")
	public String goToPage(Model model, HttpSession session){
		matchList = new ArrayList<>();
		
		for(Match match : matchRepository.findAll()) {
			matchList.add(match);
		}
		
		if(!matchList.isEmpty()){
			Collections.reverse(matchList);

			model.addAttribute("matches", matchList);
			model.addAttribute("teams", teamRepository.findAll());
			model.addAttribute("stadiums", stadiumRepository.findAll());
			model.addAttribute("matchStats", matchStatRepository.findAll());
			
			return "matchResults";
			
		} else {
			model.addAttribute("emptyListInfo", true);
			return "matchResults";
		}
	}
}
