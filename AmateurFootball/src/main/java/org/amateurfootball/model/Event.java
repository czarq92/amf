package org.amateurfootball.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name = "EVENT")
public class Event implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id_event;
	@Min(value = 1, message = "Czas zdarzenia z przedziału 1-90 minuta")
	@Max(value = 90, message = "Czas zdarzenia z przedziału 1-90 minuta")
	private int event_time;
	private String event_type;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_match")
	private Match match;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_player")
	private Player player;
	
	public Event() {
	}

	public Long getId_event() {
		return id_event;
	}

	public void setId_event(Long id_event) {
		this.id_event = id_event;
	}

	public String getEvent_type() {
		return event_type;
	}

	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}

	public int getEvent_time() {
		return event_time;
	}

	public void setEvent_time(int event_time) {
		this.event_time = event_time;
	}
	
	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public String toString() {
		return "Event [id_event=" + id_event + ", id_player=" + player.getId_player() + ", event_time="
				+ event_time + ", event_type=" + event_type + ", id_match=" + match.getId_match() + "]";
	}

}
