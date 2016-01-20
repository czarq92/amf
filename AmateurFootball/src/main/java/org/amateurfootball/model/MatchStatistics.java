package org.amateurfootball.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "MATCH_STATISTICS")
public class MatchStatistics implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id_statistic;
	private int first_team_goals;
	private int second_team_goals;
	private String result_match;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_match")
	private Match match;
	
	public MatchStatistics() {
	}

	public Long getId_statistic() {
		return id_statistic;
	}

	public void setId_statistic(Long id_statistic) {
		this.id_statistic = id_statistic;
	}


	public int getFirst_team_goals() {
		return first_team_goals;
	}

	public void setFirst_team_goals(int first_team_goals) {
		this.first_team_goals = first_team_goals;
	}

	public int getSecond_team_goals() {
		return second_team_goals;
	}

	public void setSecond_team_goals(int second_team_goals) {
		this.second_team_goals = second_team_goals;
	}

	public String getResult_match() {
		return result_match;
	}

	public void setResult_match(String result_match) {
		this.result_match = result_match;
	}
	
	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	@Override
	public String toString() {
		return "MatchStatistics [id_statistic=" + id_statistic + ", id_match=" + match.getId_match() + ", first_team_goals="
				+ first_team_goals + ", second_team_goals=" + second_team_goals + ", result_match=" + result_match
				+ "]";
	}

}
