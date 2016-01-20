package org.amateurfootball.service;

import org.springframework.stereotype.Service;

@Service
public class TabConvertService {

	public int[] convertStringToIntTab(String s){
		int[] result;
		String[] tmp;
		
		tmp = s.split("-");
		
		result = new int[tmp.length];
		
		for (int i = 0; i < tmp.length; i++) {
			result[i] = Integer.parseInt(tmp[i]);
		}
		
		return result;
	}
}
