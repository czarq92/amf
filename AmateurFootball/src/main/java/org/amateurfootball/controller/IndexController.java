package org.amateurfootball.controller;


import java.util.List;

import org.amateurfootball.model.Match;
import org.amateurfootball.repository.TeamRepository;
import org.amateurfootball.service.MiniTableService;
import org.amateurfootball.service.TopNewsService;
import org.amateurfootball.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@Autowired
	private MiniTableService miniTableService;
	@Autowired
	private TopNewsService topNewsService;
	@Autowired
	private VideoService videoService;
	@Autowired
	private TeamRepository teamRepository;
	
	@RequestMapping(value={"/", "/index"})
	public String index(Model model){

		model.addAttribute("teams", miniTableService.getTopTeamList());
		model.addAttribute("players", miniTableService.getTopPlayerList());
		model.addAttribute("news", topNewsService.getTopNewsList());
		
		List<Match> matchesList = videoService.getLastMatches();
		
		model.addAttribute("matchesList", matchesList);
		model.addAttribute("teamsList", videoService.getPlayedTeams(matchesList));
		
		return "index";
	}
	
	@RequestMapping(value = "/contact")
	public String contact(){
		return "contact";
	}
	
	@RequestMapping(value = "/infoSite")
	public String infoAboutSite(){
		return "infoSite";
	}
	
}
