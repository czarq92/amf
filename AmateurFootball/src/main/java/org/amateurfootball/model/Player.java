package org.amateurfootball.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PLAYER")
public class Player implements Serializable, Comparable<Player>{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id_player;
	
	@Size(min=3, max=20, message = "Imie musi mieć od 3 do 20 znaków.")
	private String name;
	@Size(min=3, max=20, message = "Nazwisko musi mieć od 3 do 20 znaków.")
	private String surname;
	@Size(min=3, max=20, message = "Pozycja musi mieć od 3 do 20 znaków.")
	private String position;
	@Min(value=10, message = "Gracz musi mieć więcej niż 10 lat.") 
	private int age;
	@Min(value=1, message = "Gracz musi mieć numer większy od 0")
	private int number;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_team")
	private Team team;
	
	public Player() {
	}

	
	public Long getId_player() {
		return id_player;
	}

	public void setId_player(Long id_player) {
		this.id_player = id_player;
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}


	@Override
	public String toString() {
		return "Player [id_player=" + id_player + ", name=" + name + ", surname=" + surname + ", position=" + position
				+ ", age=" + age + ", number=" + number + "]";
	}
	
	@Override
	public int compareTo(Player comparedPlayer) {
		int compareNumber = (comparedPlayer).getNumber();
		
		return this.number-compareNumber;
	}
	
}
