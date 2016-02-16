package org.amateurfootball.controller;

import org.amateurfootball.model.MatchStatistics;
import org.amateurfootball.repository.TeamRepository;
import org.amateurfootball.service.MiniTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StatisticsController {
	
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private MiniTableService miniTableService;

	@RequestMapping(value="/statistics")
	public String goStatistics(){
		
		return "statistics";
	}
	@RequestMapping(value="/teamsStat")
	public String goMatchesStat(Model model){
		
		model.addAttribute("teams", miniTableService.getTeamStatList());
		
		return "teamsStat";
	}
	@RequestMapping(value="/playersStat")
	public String goPlayersStat(Model model){
		
		model.addAttribute("players", miniTableService.getPlayerStatList());
		
		return "playersStat";
	}
	
}
