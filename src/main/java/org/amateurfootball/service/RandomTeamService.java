package org.amateurfootball.service;

import java.util.List;
import java.util.Random;

import org.amateurfootball.model.TeamChoosenDate;
import org.springframework.stereotype.Service;

@Service
public class RandomTeamService {

	public TeamChoosenDate randomTeam(List<TeamChoosenDate> teamChoosenList){
		Random rand = new Random();
		
		return teamChoosenList.get(rand.nextInt(teamChoosenList.size()));
	}
}
