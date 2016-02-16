package org.amateurfootball.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StatisticsController {

	@RequestMapping(value="/statistics")
	public String goStatistics(){
		
		return "statistics";
	}
	@RequestMapping(value="/matchesStat")
	public String goMatchesStat(){
		
		return "matchesStat";
	}
	@RequestMapping(value="/playersStat")
	public String goPlayersStat(){
		
		return "playersStat";
	}
	
}
