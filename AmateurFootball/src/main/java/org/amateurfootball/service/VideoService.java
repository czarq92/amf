package org.amateurfootball.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.amateurfootball.model.Match;
import org.amateurfootball.model.Team;
import org.amateurfootball.repository.MatchRepository;
import org.amateurfootball.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoService {
	
	@Autowired
	private MatchRepository matchRepository;
	@Autowired
	private TeamRepository teamRepository;
	
	private List<Match> lastMatches;
	private List<Team> playedTeams;
	
	public List<Match> getLastMatches(){
		lastMatches = new ArrayList<>();
		
		List<Match> tmpList = matchRepository.findAll();
		
		final int numberOfelements = 2;
		int counter = 0;
		
		Collections.reverse(tmpList);
		
		for (Match match : tmpList) {
			if(match.getMatchStatistics() != null){
				counter++;
				
				if(counter <= numberOfelements){
					lastMatches.add(match);
				}
			}
			if(counter == numberOfelements) break;
		}		

		System.out.println(lastMatches.toString());
		
		return lastMatches;
	}
	
	public List<Team> getPlayedTeams(List<Match> matchList){
		playedTeams = new ArrayList<>();
		
		for (Match match : matchList) {
			for (Team team : teamRepository.findAll()) {
				if((long) match.getFirst_team_id() == team.getId_team()  ){
					playedTeams.add(team);
				}
				if((long) match.getSecond_team_id() == team.getId_team()  ){
					playedTeams.add(team);
				}
			}
		}
		
		return playedTeams;
	}
	
}
