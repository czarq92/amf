package org.amateurfootball.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.amateurfootball.model.Event;
import org.amateurfootball.model.Player;
import org.amateurfootball.model.Team;
import org.amateurfootball.repository.EventRepository;
import org.amateurfootball.repository.PlayerRepository;
import org.amateurfootball.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

class PlayerStat {
	private Long id_PlayerStat;
	private String name;
	private int goals;
	private int number;
	private int ranked;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGoals() {
		return goals;
	}
	public void setGoals(int goals) {
		this.goals = goals;
	}
	public Long getId_PlayerStat() {
		return id_PlayerStat;
	}
	public void setId_PlayerStat(Long id_PlayerStat) {
		this.id_PlayerStat = id_PlayerStat;
	}
	public int getRanked() {
		return ranked;
	}
	public void setRanked(int ranked) {
		this.ranked = ranked;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "PlayerStat [id_PlayerStat=" + id_PlayerStat + ", name=" + name + ", goals=" + goals + ", number="
				+ number + ", ranked=" + ranked + "]";
	}
}

@Service
public class MiniTableService {
	
	@Autowired
	private TeamRepository teamRepository;
	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	private EventRepository eventRepository;
	
	private List<Team> teamList;
	private List<PlayerStat> tmpPlayerStatList;
	private List<PlayerStat> playerStatList;

	public List<Team> getTopTeamList(){
		teamList = new ArrayList<>();

		final int elemntsInTable = 5;
		int counter = 0;
		
		List<Team> tmpTeamList = teamRepository.findAll();
		
		Collections.sort(tmpTeamList);
		Collections.reverse(tmpTeamList);
		
		for (Team team : tmpTeamList) {

			if(counter < elemntsInTable){
				teamList.add(team);
			} else {
				break;
			}
			++counter;
		}
		
		return teamList;
	}
	
	public List<PlayerStat> getTopPlayerList(){
		PlayerStat playerStat;
		tmpPlayerStatList = new LinkedList<>();
		playerStatList = new LinkedList<>();

		final int elemntsInTable = 5;
		int counter = 0;
		int goals;
		
		for (Player player : playerRepository.findAll()) {
			playerStat = new PlayerStat();
			
			goals = 0;
			
			for (Event event : eventRepository.findAll()) {
				if(event.getPlayer().getId_player() == player.getId_player() && event.getEvent_type().equals("bramka")){
					playerStat.setId_PlayerStat(player.getId_player());
					
					playerStat.setName(player.getName() + " " + player.getSurname());
					
					playerStat.setNumber(player.getNumber());
					
					goals++;
					
					playerStat.setGoals(goals);
				}
			}
			if(goals > 0){
				tmpPlayerStatList.add(playerStat);
				goals = 0;
			}
		}
		
		Collections.sort(tmpPlayerStatList, new Comparator<PlayerStat>(){   
			   @Override
			   public int compare(PlayerStat p1, PlayerStat p2){
					if(p1.getGoals() < p2.getGoals()) return -1; 
					if(p1.getGoals() > p2.getGoals()) return 1;
					return 0;
					}
			}); 
		
		Collections.reverse(tmpPlayerStatList);
		
		for (PlayerStat p : tmpPlayerStatList) {
			if(counter < elemntsInTable){
				p.setRanked(counter+1);
				playerStatList.add(p);
			} else {
				break;
			}
			++counter;
		}
		
		return playerStatList ;
	}
}
