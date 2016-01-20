package org.amateurfootball.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "COACH")
public class Coach implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id_coach;

	@Size(min=3, max=20, message = "Imie musi mieć od 3 do 20 znaków.")
	private String name;
	@Size(min=3, max=20, message = "Nazwisko musi mieć od 3 do 20 znaków.")
	private String surname;
	@Size(min=4, max=20, message = "Haslo musi miec od 4 do 20 znaków.")
	private String password;
	@Size(min=4, max=20, message = "Login musi mieć od 4 do 20 znaków.")
	private String login;
	
	@OneToOne(mappedBy = "coach", fetch = FetchType.LAZY)
	private Team team;
	
	public Coach() {
	}
	

	public Long getId_coach() {
		return id_coach;
	}

	public void setId_coach(Long id_coach) {
		this.id_coach = id_coach;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public Team getTeam() {
		return team;
	}


	public void setTeam(Team team) {
		this.team = team;
	}


	@Override
	public String toString() {
		return "Coach [id_coach=" + id_coach + ", name=" + name + ", surname=" 
				+ surname + ", password=" + password + ", login=" + login + "]";
	}

	
}
