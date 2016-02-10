package org.amateurfootball.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.amateurfootball.model.Team;
import org.amateurfootball.model.TeamChoosenDate;
import org.amateurfootball.repository.MatchRepository;
import org.amateurfootball.repository.TeamChoosenDateRepository;
import org.amateurfootball.repository.TeamRepository;
import org.amateurfootball.service.DateService;
import org.amateurfootball.service.MatchService;
import org.amateurfootball.service.RandomTeamService;
import org.amateurfootball.service.TabConvertService;
import org.amateurfootball.service.TeamService;
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
	@Autowired
	private TeamService teamService;
	@Autowired
	private MatchService matchService;
	
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
//					teamChoosenDateRepository.save(choosenDate);
					
					if( !findOpponent(coachTeamId, choosenDate) ){
						session.setAttribute("NoOpponent", true);
						return "redirect:/coachSite";
					} else {
						session.setAttribute("NoOpponent", false);
						return "redirect:/coachSite";
					}
				} else {
					model.addAttribute("wrongDate", true);
					return "notifyTeam";
				}
			} else if(dateInTab[1] > dateService.monthToday()){
//				teamChoosenDateRepository.save(choosenDate);
				
				if( !findOpponent(coachTeamId, choosenDate) ){
					session.setAttribute("NoOpponent", true);
					return "redirect:/coachSite";
				} else {
					session.setAttribute("NoOpponent", false);
					return "redirect:/coachSite";
				}
			}
		} else if(dateInTab[2] > dateService.yearToday()){
//			teamChoosenDateRepository.save(choosenDate);
			
			if( !findOpponent(coachTeamId, choosenDate) ){
				session.setAttribute("NoOpponent", true);
				return "redirect:/coachSite";
			} else {
				session.setAttribute("NoOpponent", false);
				return "redirect:/coachSite";
			}
		} 
		return "redirect:/coachSite";
	}
	
	public boolean findOpponent(long coachTeamId, TeamChoosenDate choosenDate){
		opponentTeam = searchOpponentWithTheSameDate(coachTeamId, choosenDate.getDate());
		
		if (opponentTeam == null){
			return false;
		}
		
		System.out.println(opponentTeam);
		return true;
	}
	
	public Team searchOpponentWithTheSameDate(long hostTeamId, String choosenDate){
		List<TeamChoosenDate> teamDateList;
		
		teamDateList = teamChoosenDateRepository.findAll();
		
		int attempts = 3;
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
	
	
//	public String verifyData(String choosenDate, int teamId){
//		List<TeamChoosenDate> teamDateList = new ArrayList<>();
//		teamDateList = teamChoosenDateRepository.findAll();
//		
//		int opponentId_stadium;
//		
//		String startHour = "8:00";
//		
//		//dla WSZYSTKICH zgłoszonych drużyn...
//		for (TeamChoosenDate obj : teamDateList) {
//			//wylosuj ID drużyny z listy zgłoszonych drużyn
//			int randomTeamId = randomTeamService.randomTeam(teamDateList);
//			
//			//sprawdź czy Moja zgłoszona data == Data zgłoszenia innej drużyny
//			//... i czy nie wylosowałem sam siebie
//			if(choosenDate == obj.getDate() && teamId != randomTeamId){
//				Match newMatch = new Match();
//				
//				newMatch.setFirst_team_id(teamId);
//				newMatch.setSecond_team_id(randomTeamId);
//				opponentId_stadium = teamService.findStadiumIdByTeamId(randomTeamId);
//				newMatch.setDate(choosenDate);
//				
//				if(matchService.verifyHour(startHour, choosenDate ) == startHour){
//					newMatch.setHour(startHour);
//				} else {
//					startHour = matchService.verifyHour(startHour, choosenDate);
//					newMatch.setHour(startHour);
//				}
//				
//				if(matchService.checkFreeStadium(opponentId_stadium, choosenDate, startHour) == true) {
//					newMatch.setId_stadium(opponentId_stadium);
//				} else {
//					newMatch.setId_stadium(teamService.findStadiumIdByTeamId(teamId));
//				}
//				
//				System.out.println(newMatch);
//				//matchRepository.save(newMatch);
//				return "redirect:/coachSite";
//			} else {
//				//??
//			}
//		}
//		return "redirect:/coachSite";
//	}
	
}
