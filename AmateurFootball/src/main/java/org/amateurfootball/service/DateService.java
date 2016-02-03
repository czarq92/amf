package org.amateurfootball.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class DateService {
	Date dateObj = new Date();
	
	public int dayToday(){
		int day = 0;
		
		DateFormat dateFormat = new SimpleDateFormat("dd");
		
		day = Integer.parseInt(dateFormat.format(dateObj));
		
		return day;
	}
	
	public int monthToday(){
		int month = 0;
		
		DateFormat dateFormat = new SimpleDateFormat("M");
		
		month = Integer.parseInt(dateFormat.format(dateObj));
		
		return month;
	}
	
	public int yearToday(){
		int year = 0;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		
		year = Integer.parseInt(dateFormat.format(dateObj));
		
		return year;
	}
}
