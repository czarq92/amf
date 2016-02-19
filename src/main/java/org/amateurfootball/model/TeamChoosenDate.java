package org.amateurfootball.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TEAM_CHOOSEN_DATE")
public class TeamChoosenDate implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id_choosen_date;
	@NotNull
	private String date;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_team")
	private Team team;

	
	public TeamChoosenDate() {
	}

	public Long getId_choosen_date() {
		return id_choosen_date;
	}

	public void setId_choosen_date(Long id_choosen_date) {
		this.id_choosen_date = id_choosen_date;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}


	@Override
	public String toString() {
		return "TeamChoosenDate [id_choosen_date=" + id_choosen_date + ", date=" + date + "]";
	}
	
}
