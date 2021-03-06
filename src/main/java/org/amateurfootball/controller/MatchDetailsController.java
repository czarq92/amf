package org.amateurfootball.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.amateurfootball.model.Event;
import org.amateurfootball.repository.EventRepository;
import org.amateurfootball.repository.MatchRepository;
import org.amateurfootball.repository.MatchStatisticsRepository;
import org.amateurfootball.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MatchDetailsController {
	
	@Autowired
	private MatchRepository matchRepository;
	@Autowired
	private MatchStatisticsRepository matchStatisticsRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private TeamRepository teamRepository;
	
	@RequestMapping(value = "/matchDetails/{idMatch}")
	public String details(@PathVariable("idMatch") long idMatch, Model model, HttpSession session){
		
		model.addAttribute("match", matchRepository.findOne(idMatch));
		model.addAttribute("matchDetails", matchStatisticsRepository.findOne(idMatch));
		model.addAttribute("teams", teamRepository.findAll());
		
		List<Event> eventList = eventRepository.findAll();
		
		Collections.sort(eventList);
		 
		model.addAttribute("events", eventList);
		
		Long firstTeamId = (long) matchRepository.findOne(idMatch).getFirst_team_id();
		Long secondTeamId = (long) matchRepository.findOne(idMatch).getSecond_team_id();
		
		model.addAttribute("firstTeamId", firstTeamId);
		model.addAttribute("secondTeamId", secondTeamId);
		model.addAttribute("matchId", idMatch);
		
		return "matchDetails";
	}

}
