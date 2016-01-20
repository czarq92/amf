package org.amateurfootball.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "MATCH_")
public class Match implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id_match;
	
	private int first_team_id;
	private int second_team_id;
	private String date;
	private String hour;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_stadium")
	private Stadium stadium;

	@OneToMany(mappedBy = "match", fetch = FetchType.LAZY)
	private List<Event> eventList;
	
	@OneToOne(mappedBy = "match", fetch = FetchType.LAZY)
	private MatchStatistics matchStatistics;;
	
	public Match() {
	}

	public Long getId_match() {
		return id_match;
	}

	public void setId_match(Long id_match) {
		this.id_match = id_match;
	}

	public int getFirst_team_id() {
		return first_team_id;
	}

	public void setFirst_team_id(int first_team_id) {
		this.first_team_id = first_team_id;
	}

	public int getSecond_team_id() {
		return second_team_id;
	}

	public void setSecond_team_id(int second_team_id) {
		this.second_team_id = second_team_id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}
	
	public Stadium getStadium() {
		return stadium;
	}

	public void setStadium(Stadium stadium) {
		this.stadium = stadium;
	}

	public List<Event> getEventList() {
		return eventList;
	}

	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}
	
	public MatchStatistics getMatchStatistics() {
		return matchStatistics;
	}

	public void setMatchStatistics(MatchStatistics matchStatistics) {
		this.matchStatistics = matchStatistics;
	}

	@Override
	public String toString() {
		return "Match [id_match=" + id_match + ", first_team_id=" + first_team_id + ", second_team_id=" + second_team_id
				+ ", id_stadium=" + stadium.getId_stadium() + ", date=" + date + ", hour=" + hour + "]";
	}

}
