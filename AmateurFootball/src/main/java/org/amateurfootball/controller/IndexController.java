package org.amateurfootball.controller;


import org.amateurfootball.service.MiniTableService;
import org.amateurfootball.service.TopNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@Autowired
	private MiniTableService miniTableService;
	@Autowired
	private TopNewsService topNewsService;
	
//	@RequestMapping(value="/test")
//	@ResponseBody
//	Collection<Player> collection(){
//		return this.playerRepository.findAll();
//	}
	
	@RequestMapping(value={"/", "/index"})
	public String index(Model model){

		model.addAttribute("teams", miniTableService.getTopTeamList());
		model.addAttribute("news", topNewsService.getTopNewsList());
		
		return "index";
	}
	
	@RequestMapping(value = "/contact")
	public String contact(){
		return "contact";
	}
	
}
