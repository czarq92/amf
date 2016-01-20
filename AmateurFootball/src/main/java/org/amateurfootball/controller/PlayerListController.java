package org.amateurfootball.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	
	@RequestMapping(value = "/playerList")
	public String playerListReturn(Model model){
		List<Player> playersList = new ArrayList<>();
		
		playersList = playerRepository.findAll();
		
		Collections.sort(playersList);
		
		model.addAttribute("playerList", playersList);

		return "playerList";
	}
	
}
