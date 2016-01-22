package org.amateurfootball.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TEAM")
public class Team implements Serializable, Comparable<Team>{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id_team;
	@Size(min=4, max=20, message = "Nazwa powinna zawierać od 4 do 20 znaków.")
	private String name;
	@Size(min=4, max=20, message = "Miasto powinno zawierać od 4 do 20 znaków.")
	private String city;
	private int goals_hit;
	private int goals_lost;
	private int points;
	private int ranked;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_coach")
	private Coach coach;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_stadium")
	private Stadium stadium;
	
	@OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
	private List<Player> playerList;
	
	@OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
	private List<TeamChoosenDate> teamChoosenDateList;
	
	public Team() {
	}
	

	public Long getId_team() {
		return id_team;
	}

	public void setId_team(Long id_team) {
		this.id_team = id_team;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getRanked() {
		return ranked;
	}

	public void setRanked(int ranked) {
		this.ranked = ranked;
	}


	public int getPoints() {
		return points;
	}


	public void setPoints(int points) {
		this.points = points;
	}


	public int getGoals_hit() {
		return goals_hit;
	}


	public void setGoals_hit(int goals_hit) {
		this.goals_hit = goals_hit;
	}


	public int getGoals_lost() {
		return goals_lost;
	}


	public void setGoals_lost(int goals_lost) {
		this.goals_lost = goals_lost;
	}
	
	public Coach getCoach() {
		return coach;
	}


	public void setCoach(Coach coach) {
		this.coach = coach;
	}


	public Stadium getStadium() {
		return stadium;
	}


	public void setStadium(Stadium stadium) {
		this.stadium = stadium;
	}
	
	public List<Player> getPlayerList() {
		return playerList;
	}


	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}


	@Override
	public String toString() {
		return "Team [id_team=" + id_team + ", name=" + name + ", city=" + city + ", goals_hit=" + goals_hit
				+ ", goals_lost=" + goals_lost + ", points=" + points + ", ranked=" + ranked + "]";
	}


	@Override
	public int compareTo(Team comparedTeam) {
		int comparePoints = (comparedTeam).getPoints();
		
		return this.points-comparePoints;
	}

}
