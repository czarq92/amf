package org.amateurfootball.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.amateurfootball.model.Event;
import org.amateurfootball.model.Match;
import org.amateurfootball.model.Team;
import org.amateurfootball.repository.EventRepository;
import org.amateurfootball.repository.MatchRepository;
import org.amateurfootball.repository.TeamRepository;
import org.amateurfootball.service.MatchService;
import org.amateurfootball.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AddEventController {

	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private MatchRepository matchRepository;
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private TeamService teamService;
	@Autowired
	private MatchService matchService;
	
	@RequestMapping(value = "/addEvent/{id}", method = RequestMethod.GET)
	public String eventForm(@PathVariable("id") int idMatch, Model model, HttpSession session){
		
		if( session.getAttribute("nameUserLogged") == null ){
			return "redirect:/index";
		}

		Event event = new Event();
		int tmpId = idMatch;
		
		Match findedMatch = matchRepository.findOne((long) tmpId);
		
		event.setMatch(findedMatch);
		
		model.addAttribute("event", event);
		model.addAttribute("coachPlayerList",session.getAttribute("coachPlayerList"));
		
		return "addEvent";
	}
	
	@RequestMapping(value = "/addEvent", method = RequestMethod.POST)
	public String eventSubmit(@Valid @ModelAttribute("event") Event event,  
							BindingResult bindingResult, Model model, 
							HttpSession session){
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("coachPlayerList",session.getAttribute("coachPlayerList"));
			
            return "addEvent";
        }
		
		int opponentTeamId;
		
		Long coachTeamId = (Long) session.getAttribute("idTeamUserLogged");
		Long matchId = (Long) event.getMatch().getId_match();

		String eventType = event.getEvent_type();
		String whichTeamGoals = "first";
		
		Match match = matchRepository.findOne(matchId);
		
		if(match.getFirst_team_id() == coachTeamId){
			opponentTeamId = match.getSecond_team_id();
		} else {
			opponentTeamId = match.getFirst_team_id();
			whichTeamGoals = "second";
		}
		
		eventRepository.save(event);

		if(eventType.equals("bramka")){
			teamService.updateGoalsAndPoints(coachTeamId, "coach");
			teamService.updateGoalsAndPoints(opponentTeamId, "opponent");
			teamService.updateRanking();
			
			matchService.updateMatchStatistics(matchId, whichTeamGoals);
		}
		
		return "redirect:/matchSite";
	}
	
	
}
