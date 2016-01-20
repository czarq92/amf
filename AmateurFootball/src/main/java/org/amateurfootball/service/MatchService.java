package org.amateurfootball.service;

import java.util.List;

import org.amateurfootball.model.Match;
import org.amateurfootball.model.MatchStatistics;
import org.amateurfootball.repository.MatchRepository;
import org.amateurfootball.repository.MatchStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static java.lang.Math.toIntExact;

@Service
public class MatchService {

	@Autowired
	private MatchRepository matchRepository;
	@Autowired
	private MatchStatisticsRepository matchStatisticsRepository;
	
	private boolean freeHour;
	
	public void updateMatchStatistics(long matchId, String whichTeam){
		MatchStatistics matchStat = matchStatisticsRepository.findOne(matchId);
		int goals = 0;
		String resultMatch = "";
		
		if(whichTeam == "first"){
			goals = matchStat.getFirst_team_goals();
			goals += 1;
			matchStat.setFirst_team_goals(goals);
		}else if(whichTeam == "second") {
			goals = matchStat.getSecond_team_goals();
			goals += 1;
			matchStat.setSecond_team_goals(goals);
		}
		
		resultMatch = matchStat.getFirst_team_goals() + ":" + matchStat.getSecond_team_goals();

		matchStat.setResult_match(resultMatch);
		
		matchStatisticsRepository.save(matchStat);
	}
	
	public String verifyHour(String checkedHour, String checkedDate){
		freeHour = true;
		String tmpCheckedHour = checkedHour;
		
		for (Match match : matchRepository.findAll()) {
			if(checkedDate == match.getDate() && tmpCheckedHour == match.getHour()){
				tmpCheckedHour = nextHour(tmpCheckedHour);
				freeHour = true;
				System.out.println("Nowa godzina = " + tmpCheckedHour);
			}
		}
		
		if(freeHour == true){
			return tmpCheckedHour;
		}
		
		return "null";
	}
	
	public String nextHour(String hour){
		String[] stringTab = hour.split(":"); 
		String nextHourMade;
		
		int tmp = Integer.parseInt(stringTab[0]) + 2;
		
		nextHourMade = Integer.toString(tmp) + ":00";
		
		return nextHourMade;
	}
	
//	public boolean checkFreeStadium(int id_stadiumInMatch, String matchDate, String hourMatch){
//		List<Match> listMatch = matchRepository.findAll();
//		
//		for (Match match : listMatch) {
//			if(match.getId_stadium() == id_stadiumInMatch 
//					&& match.getDate() == matchDate 
//					&& match.getHour() == hourMatch){
//					
//				return false;
//			}
//		}
//		
//		return true;
//	}
}
