package org.amateurfootball.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.amateurfootball.model.Player;
import org.amateurfootball.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/editPlayer")
public class EditPlayerController {

	@Autowired
	private PlayerRepository playerRepository;
	
	private int usedIndex_player;
	private List<Player> coachPlayerList;
	private int listSize;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
	public String editPlayerForm(Model model, HttpSession session){
		
		if(session.getAttribute("nameUserLogged") == null){
			return "redirect:/index";
		}
		
		if(session.getAttribute("idTeamUserLogged") != null) {
			
			usedIndex_player = (int) session.getAttribute("usedPlayerIndexEdit");
			coachPlayerList = (List<Player>) session.getAttribute("coachPlayerList");
			
			listSize = coachPlayerList.size();
			
			if(listSize > 0){
				model.addAttribute("player", coachPlayerList.get(usedIndex_player));
			
				return "editPlayer";
			} else {
				session.setAttribute("addPlayerInfo", true);
				
				return "redirect:/addPlayer";
			}
		}
		
		session.setAttribute("addTeamInfo", true);
		
		return "redirect:/addTeam";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String editPlayerSubmit(@Valid @ModelAttribute("player") Player player, 
			BindingResult bindingResult){
		
		if(bindingResult.hasErrors()) {
            return "editPlayer";
        }
		
		System.out.println(player.getName());
		
		playerRepository.save(player);
		
		return "redirect:/coachSite";
	}
	
	@RequestMapping(value = "/next")
	public String nextPlayer(@ModelAttribute("player") Player player, 
			BindingResult bindingResult, HttpSession session){
		
		int index = (int) session.getAttribute("usedPlayerIndexEdit");
		
		if (++index == listSize){
			session.setAttribute("usedPlayerIndexEdit", listSize-1);
		} else {
			session.setAttribute("usedPlayerIndexEdit", index);
		}
		
		return "redirect:/editPlayer";
	}
	
	@RequestMapping(value = "/previous")
	public String previousPlayer(@ModelAttribute("player") Player player, 
			BindingResult bindingResult, HttpSession session){
		
		int index = (int) session.getAttribute("usedPlayerIndexEdit");
		
		if (--index < 0){
			session.setAttribute("usedPlayerIndexEdit", 0);
		}else {
			session.setAttribute("usedPlayerIndexEdit", index);
		}
		
		return "redirect:/editPlayer";
	}
	
}
