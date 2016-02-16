package org.amateurfootball.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.amateurfootball.model.Event;
import org.amateurfootball.model.MatchStatistics;
import org.amateurfootball.model.Player;
import org.amateurfootball.model.Team;
import org.amateurfootball.repository.EventRepository;
import org.amateurfootball.repository.PlayerRepository;
import org.amateurfootball.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

class PlayerMini {
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

class PlayerStat {
	private Long id_PlayerStat;
	private String name;
	private int goals;
	private int fauls;
	private int yellowCards;
	private int redCards;
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
	public int getFauls() {
		return fauls;
	}
	public void setFauls(int fauls) {
		this.fauls = fauls;
	}
	public int getYellowCards() {
		return yellowCards;
	}
	public void setYellowCards(int yellowCards) {
		this.yellowCards = yellowCards;
	}
	public int getRedCards() {
		return redCards;
	}
	public void setRedCards(int redCards) {
		this.redCards = redCards;
	}
	@Override
	public String toString() {
		return "PlayerStat [id_PlayerStat=" + id_PlayerStat + ", name=" + name + ", goals=" + goals + ", fauls=" + fauls
				+ ", yellowCards=" + yellowCards + ", redCards=" + redCards + ", number=" + number + ", ranked="
				+ ranked + "]";
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
	private List<PlayerMini> tmpPlayerMiniList;
	private List<PlayerMini> playerMiniList;
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
	
	public List<Team> getTeamStatList(){
		List<Team> teamList = teamRepository.findAll();
		
		Collections.sort(teamList);
		Collections.reverse(teamList);
		
		return teamList;
	}
	
	
	public List<PlayerMini> getTopPlayerList(){
		PlayerMini playerStat;
		tmpPlayerMiniList = new LinkedList<>();
		playerMiniList = new LinkedList<>();

		final int elemntsInTable = 5;
		int counter = 0;
		int goals;
		
		for (Player player : playerRepository.findAll()) {
			playerStat = new PlayerMini();
			
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
				tmpPlayerMiniList.add(playerStat);
				goals = 0;
			}
		}
		
		Collections.sort(tmpPlayerMiniList, new Comparator<PlayerMini>(){   
			   @Override
			   public int compare(PlayerMini p1, PlayerMini p2){
					if(p1.getGoals() < p2.getGoals()) return -1; 
					if(p1.getGoals() > p2.getGoals()) return 1;
					return 0;
					}
			}); 
		
		Collections.reverse(tmpPlayerMiniList);
		
		for (PlayerMini p : tmpPlayerMiniList) {
			if(counter < elemntsInTable){
				p.setRanked(counter+1);
				playerMiniList.add(p);
			} else {
				break;
			}
			++counter;
		}
		
		return playerMiniList ;
	}
	
	public List<PlayerStat> getPlayerStatList(){
		PlayerStat playerStat;
		tmpPlayerStatList = new LinkedList<>();
		playerStatList = new LinkedList<>();

		int counter = 0;
		int goals;
		int fauls;
		int yellowCards;
		int redCards;
		
		for (Player player : playerRepository.findAll()) {
			playerStat = new PlayerStat();
			
			goals = 0;
			fauls = 0;
			yellowCards = 0;
			redCards = 0;
			
			for (Event event : eventRepository.findAll()) {
				if(event.getPlayer().getId_player() == player.getId_player()){
					playerStat.setId_PlayerStat(player.getId_player());
					playerStat.setName(player.getName() + " " + player.getSurname());
					playerStat.setNumber(player.getNumber());
						
					if(event.getEvent_type().equals("bramka")){	
						goals++;
						playerStat.setGoals(goals);
					}
					if(event.getEvent_type().equals("faul")){
						fauls++;
						playerStat.setFauls(fauls);
					}
					if(event.getEvent_type().equals("żółta kartka")){
						yellowCards++;
						playerStat.setYellowCards(yellowCards);
					}
					if(event.getEvent_type().equals("czerwona kartka")){
						redCards++;
						playerStat.setRedCards(redCards);
					}
				}	else {
						playerStat.setId_PlayerStat(player.getId_player());
						playerStat.setName(player.getName() + " " + player.getSurname());
						playerStat.setNumber(player.getNumber());
						playerStat.setGoals(goals);
						playerStat.setFauls(fauls);
						playerStat.setYellowCards(yellowCards);
						playerStat.setRedCards(redCards);
					}
			}
			
			tmpPlayerStatList.add(playerStat);
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
			p.setRanked(counter+1);
			playerStatList.add(p);
			++counter;
		}
		
		return playerStatList ;
	}
}
