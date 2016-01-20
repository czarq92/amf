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
}
