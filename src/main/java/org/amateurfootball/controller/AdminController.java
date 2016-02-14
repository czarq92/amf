package org.amateurfootball.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.amateurfootball.model.News;
import org.amateurfootball.repository.NewsRepository;
import org.amateurfootball.service.DateService;
import org.amateurfootball.service.TopNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {
	
	@Autowired
	private TopNewsService topNewsService;
	@Autowired
	private DateService dateService;
	@Autowired
	private NewsRepository newsRepository;
	
	private String name;
	private String loggedUser;
	private String todayDate;
	
	@RequestMapping(value="/admin")
	public String redirect(Model model, HttpSession session){
		name = "admin";
		loggedUser = (String) session.getAttribute("nameUserLogged");
		
		if(loggedUser != null && loggedUser.equals(name) ){
			model.addAttribute("news", topNewsService.getTopNewsList());
			model.addAttribute("newNews", new News());
			
			return "admin";
		}
		
		return "redirect:/index";
	}
	

	@RequestMapping(value = "/addNews", method = RequestMethod.POST)
	public String registrationSubmit(@Valid @ModelAttribute("newNews") News news, 
							BindingResult bindingResult, Model model, 
							HttpSession session){
		
		if( bindingResult.hasErrors() ) {
            return "admin";
        }
		
		todayDate = dateService.yearToday() + "-";
		
		if(dateService.monthToday() < 10){
			todayDate += "0" + dateService.monthToday() + "-";
		} else {
			todayDate += dateService.monthToday() + "-";
		}
		
		if(dateService.dayToday() < 10){
			todayDate += "0" + dateService.dayToday();
		}
		else{
			todayDate += dateService.dayToday();
		}
		
		news.setDate(todayDate);
		
		newsRepository.save(news);
		
		return "redirect:/admin";
	}
}
