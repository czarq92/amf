package org.amateurfootball.controller;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.amateurfootball.model.Match;
import org.amateurfootball.model.Stadium;
import org.amateurfootball.model.Team;
import org.amateurfootball.model.TeamChoosenDate;
import org.amateurfootball.repository.MatchRepository;
import org.amateurfootball.repository.TeamChoosenDateRepository;
import org.amateurfootball.repository.TeamRepository;
import org.amateurfootball.service.DateService;
import org.amateurfootball.service.RandomTeamService;
import org.amateurfootball.service.TabConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NotifyTeamController {
	
	@Autowired
	private TeamChoosenDateRepository teamChoosenDateRepository;
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private MatchRepository matchRepository;
	@Autowired
	private TabConvertService tabConvertService;
	@Autowired
	private DateService dateService;
	@Autowired
	private RandomTeamService randomTeamService;
	
	private Team opponentTeam;
	
	@RequestMapping(value = "/notifyTeam", method = RequestMethod.GET)
	public String notifyTeamForm(Model model, HttpSession session){
		
		if( session.getAttribute("nameUserLogged") == null ){
			return "redirect:/index";
		}
		
		if( session.getAttribute("idTeamUserLogged") == null ){
			session.setAttribute("addTeamBeforeNotify", true);
			
			return "redirect:/addTeam";
		}
		
		model.addAttribute("choosenDate", new TeamChoosenDate());
		
		return "notifyTeam";
	}
	
	@RequestMapping(value = "/notifyTeam", method = RequestMethod.POST)
	public String notifyTeamSubmit(@ModelAttribute("choosenDate") TeamChoosenDate choosenDate, 
			Model model, HttpSession session){
		
		if( session.getAttribute("nameUserLogged") == null ){
			return "redirect:/index";
		}
		
		Long coachTeamId = (Long) session.getAttribute("idTeamUserLogged");

		int[] dateInTab = new int[3];
		
		Team findedCoachTeam = teamRepository.findOne(coachTeamId);
		
		choosenDate.setTeam(findedCoachTeam);
		
		dateInTab = tabConvertService.convertStringToIntTab(choosenDate.getDate());
		
		if(dateInTab[2] == dateService.yearToday()){
			if(dateInTab[1] == dateService.monthToday()){
				if(dateInTab[0] > dateService.dayToday()){
					teamChoosenDateRepository.save(choosenDate);
					
					if( !setTheMatch(coachTeamId, choosenDate) ){
						session.setAttribute("noOpponent", true);
						session.setAttribute("findOpponent", false);
						
						return "redirect:/coachSite";
					} else {
						session.setAttribute("noOpponent", false);
						session.setAttribute("findOpponent", true);
						
						return "redirect:/coachSite";
					}
				} else {
					model.addAttribute("wrongDate", true);
					
					return "notifyTeam";
				}
			} else if(dateInTab[1] > dateService.monthToday()){
				teamChoosenDateRepository.save(choosenDate);
				
				if( !setTheMatch(coachTeamId, choosenDate) ){
					session.setAttribute("noOpponent", true);
					session.setAttribute("findOpponent", false);
					
					return "redirect:/coachSite";
				} else {
					session.setAttribute("noOpponent", false);
					session.setAttribute("findOpponent", true);
					
					return "redirect:/coachSite";
				}
			}
		} else if(dateInTab[2] > dateService.yearToday()){
			teamChoosenDateRepository.save(choosenDate);
			
			if( !setTheMatch(coachTeamId, choosenDate) ){
				session.setAttribute("noOpponent", true);
				session.setAttribute("findOpponent", false);
				
				return "redirect:/coachSite";
			} else {
				session.setAttribute("noOpponent", false);
				session.setAttribute("findOpponent", true);
				
				return "redirect:/coachSite";
			}
		} 
		return "redirect:/coachSite";
	}
	
	public boolean setTheMatch(long coachTeamId, TeamChoosenDate choosenDate){
		opponentTeam = searchOpponentWithTheSameDate(coachTeamId, choosenDate.getDate());
		
		if (opponentTeam == null){
			return false;
		}

		Match match = new Match();
		Random rand = new Random();
		
		Stadium opponentStadium = opponentTeam.getStadium();

		String[] tab = {"8:00","10:00","12:00","14:00","16:00","18:00","20:00"};
		int randomHourIndex = rand.nextInt(tab.length-1);
		
		match.setFirst_team_id((int) coachTeamId);
		match.setSecond_team_id(Math.toIntExact(opponentTeam.getId_team())); 
		match.setDate(choosenDate.getDate());
		match.setStadium(opponentStadium);
		match.setHour(tab[randomHourIndex]);
		
		matchRepository.save(match);
		
		deleteChoosenTeamsDate(teamRepository.findOne(coachTeamId), opponentTeam, choosenDate.getDate());
		
		return true;
	}
	
	public Team searchOpponentWithTheSameDate(long hostTeamId, String choosenDate){
		List<TeamChoosenDate> teamDateList;
		
		teamDateList = teamChoosenDateRepository.findAll();
		
		int attempts = 5;
		int i = 0;
		TeamChoosenDate opponentTeamChoosenDate = null;
		
		opponentTeam = null;
		
		boolean finded = false;
		
		do {
			finded = false;
			++i;
			opponentTeamChoosenDate = randomTeamService.randomTeam(teamDateList);
			
			if(hostTeamId != opponentTeamChoosenDate.getTeam().getId_team() && 
				choosenDate.equals(opponentTeamChoosenDate.getDate())){
				finded = true;
				
				return opponentTeamChoosenDate.getTeam();
			}
			
			if(i == attempts){
				finded = true;
			}
			
		} while(!finded);
		
		return opponentTeam;
	}
	
	public void deleteChoosenTeamsDate(Team firstTeam, Team secondTeam, String date){
		findTeamAndDeleteChoosendate(firstTeam, date);
		findTeamAndDeleteChoosendate(secondTeam, date);
	}
	
	public void findTeamAndDeleteChoosendate(Team team, String date){
		List<TeamChoosenDate> teamChoosenDateList = teamChoosenDateRepository.findAll();
		
		for (TeamChoosenDate teamDate : teamChoosenDateList) {
			if(teamDate.getTeam() == team && teamDate.getDate().equals(date)){
				teamChoosenDateRepository.delete(teamDate);
				break;
			}
		}
	}
}
