package org.amateurfootball.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.amateurfootball.model.Player;
import org.amateurfootball.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PlayerListController {

	@Autowired
	private PlayerRepository playerRepository;
	
	private int start = 0;
	private int end = 5;
	private int listSize;
	
	private final int numberOfElements = 8;

	@RequestMapping(value = "/playerList")
	public String playerListReturn(Model model, HttpSession session){
		List<Player> tmpList;
		List<Player> playersList = new ArrayList<>();
		
		tmpList = playerRepository.findAll();
		Collections.sort(tmpList);
		
		listSize = tmpList.size();
		int counter = 0;
		
		start = 0;
		end = numberOfElements;
		
		for (Player player : tmpList) {
			counter++;
			
			if(counter >= start && counter <= end){
				playersList.add(player);
			}
		}
		
		model.addAttribute("playerList", playersList);

		return "playerList";
	}
	
	@RequestMapping(value = "/previousPlayers")
	public String previous(Model model){
		List<Player> list = new ArrayList<>();
		List<Player> playersList = new ArrayList<>();
		
		list = playerRepository.findAll();
		Collections.sort(list);
		
		listSize = list.size();
		
		int counter = 0;
		
		if(start > numberOfElements){
			start -= numberOfElements;
			
			if(end == listSize){
				end = start + numberOfElements;
			}else {
				end -= numberOfElements;
			}
		} else{
			start = 0;
			end = numberOfElements;
		}
		
		for (Player player : list) {
			counter++;
			
			if(counter > start && counter <= end){
				playersList.add(player);
			}
		}
		
		model.addAttribute("playerList", playersList);
		
		return "playerList";
	}
	
	@RequestMapping(value = "/nextPlayers")
	public String next(Model model){
		List<Player> list = new ArrayList<>();
		List<Player> playersList = new ArrayList<>();
		
		list = playerRepository.findAll();
		Collections.sort(list);
		
		listSize = list.size();
		
		int counter = 0;
		
		if(end < listSize){
			start += numberOfElements;
			
			if( (end + numberOfElements) < listSize){
				end += numberOfElements;
			} else{
				end = listSize;
			}
		}
		
		for (Player player : list) {
			counter++;
			
			if(counter > start && counter <= end){
				playersList.add(player);
			}
		}
		
		model.addAttribute("playerList", playersList);
		
		return "playerList";
	}
	
}
