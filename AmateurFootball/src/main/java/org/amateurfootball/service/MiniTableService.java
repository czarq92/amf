package org.amateurfootball.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.amateurfootball.model.Team;
import org.amateurfootball.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MiniTableService {
	@Autowired
	private TeamRepository teamRepository;
	
	private List<Team> teamList;

	public List<Team> getTopTeamList(){
		teamList = new ArrayList<>();
		
		final int elemntsInTable = 5;
		int counter = 0;
		
		for (Team team : teamRepository.findAll()) {
			if(counter < elemntsInTable){
				teamList.add(team);
			} else {
				break;
			}
			++counter;
		}
		
		Collections.sort(teamList);
		Collections.reverse(teamList);
		
		return teamList;
	}
	
}
