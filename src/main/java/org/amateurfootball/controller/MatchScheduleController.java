package org.amateurfootball.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.amateurfootball.model.Match;
import org.amateurfootball.repository.MatchRepository;
import org.amateurfootball.repository.StadiumRepository;
import org.amateurfootball.repository.TeamRepository;
import org.amateurfootball.service.DateService;
import org.amateurfootball.service.TabConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MatchScheduleController {

	@Autowired
	private MatchRepository matchRepository;
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private StadiumRepository stadiumRepository;
	@Autowired
	private DateService dateService;
	@Autowired
	private TabConvertService tabConvertService;
	
	private int[] todayDateTab;
	private List<Match> matchList;
	private int[] matchDateTab;
	
	@RequestMapping(value = "/matchSchedule")
	public String goToPage(Model model, HttpSession session){
		todayDateTab = new int[3];
		todayDateTab[0] = dateService.dayToday();
		todayDateTab[1] = dateService.monthToday();
		todayDateTab[2] = dateService.yearToday();
		matchList = new ArrayList<>();
		
		for (Match match : matchRepository.findAll()) {
			String date = match.getDate();
			
			matchDateTab = tabConvertService.convertStringToIntTab(date);
			
			if(matchDateTab[2] == todayDateTab[2]){
				if(matchDateTab[1] == todayDateTab[1]){
					if (matchDateTab[0] >= todayDateTab[0]){
						matchList.add(match);
					}
				} else if(matchDateTab[1] > todayDateTab[1]){
					matchList.add(match);
				}
			} else if(matchDateTab[2] > todayDateTab[2]){
				matchList.add(match);
			}
		}
		
		model.addAttribute("matches", matchList);
		model.addAttribute("teams", teamRepository.findAll());
		model.addAttribute("stadiums", stadiumRepository.findAll());
		
		return "matchSchedule";
	}
	
	
}
