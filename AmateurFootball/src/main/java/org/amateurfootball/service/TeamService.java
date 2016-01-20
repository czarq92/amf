package org.amateurfootball.service;

import org.amateurfootball.model.Team;
import org.amateurfootball.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.Math.toIntExact;

import java.util.Collections;
import java.util.List;

@Service
public class TeamService {

	@Autowired
	private TeamRepository teamRepository;
	
	public int findStadiumIdByTeamId(int searched_id){
		for (Team team : teamRepository.findAll()) {
			int team_id = toIntExact(team.getId_team());
			
			if(team_id == searched_id){
				System.out.println("TeamService-> teamStadiumID = " + toIntExact(team.getStadium().getId_stadium()));
				return toIntExact(team.getStadium().getId_stadium());
			}
		}
		return 0;
	}
	
	public int findLastTeamInRanking(){
		int rankingNumber=0;
		
		for(Team team : teamRepository.findAll()){
			if(team.getRanked() > rankingNumber){
				rankingNumber = team.getRanked();
			}
		}
		
		return rankingNumber + 1;
	}
	
	public void updateGoalsAndPoints(long teamId, String owner){
		Team team = teamRepository.findOne(teamId);
		
		int goals = 0;
		int points = team.getPoints();
		
		if(owner == "coach"){
			goals = team.getGoals_hit();

			goals += 1;
			team.setGoals_hit(goals);
			
			points += 2;
			team.setPoints(points);
		}else if(owner == "opponent"){
			goals = team.getGoals_lost();
			
			goals += 1;
			team.setGoals_lost(goals);
			
			if(points > 0){
				points -= 1;
				team.setPoints(points);
			}
		}
		
		teamRepository.save(team);
	}
	
	public void updateRanking(){
		List<Team> teamList = teamRepository.findAll();

		int i = 1;
		
		Collections.sort(teamList);
		Collections.reverse(teamList);
		
		for (Team team : teamList) {
			team.setRanked(i++);
			teamRepository.save(team);
		}
	}
	
}
