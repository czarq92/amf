package org.amateurfootball.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "STADIUM")
public class Stadium implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id_stadium;
	private String name;
	private String city;
	private String address;

	@OneToMany(mappedBy = "stadium", fetch = FetchType.LAZY)
	private List<Team> teamList;
	
	@OneToMany(mappedBy = "stadium", fetch = FetchType.LAZY)
	private List<Match> matchList;
	
	public Stadium() {
	}

	public Long getId_stadium() {
		return id_stadium;
	}

	public void setId_stadium(Long id_stadium) {
		this.id_stadium = id_stadium;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public List<Team> getTeamList() {
		return teamList;
	}

	public void setTeamList(List<Team> teamList) {
		this.teamList = teamList;
	}
	
	public List<Match> getMatchList() {
		return matchList;
	}

	public void setMatchList(List<Match> matchList) {
		this.matchList = matchList;
	}

	@Override
	public String toString() {
		return "Stadium [id_stadium=" + id_stadium + ", name=" + name + ", city=" + city + ", address=" + address + "]";
	}
	
}
