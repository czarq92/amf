package org.amateurfootball.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "NEWS")
public class News implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id_news;
	private String date;
	
	@Size(min=10, max=150, message = "Wiadomość musi być dłuższa niż 10 i krótsza niż 150 znaków.")
	private String message;
	
	public News() {
	}
	
	public Long getId() {
		return id_news;
	}
	public void setId(Long id) {
		this.id_news = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String data) {
		this.date = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "News [id=" + id_news + ", data=" + date + ", message=" + message + "]";
	}

}
