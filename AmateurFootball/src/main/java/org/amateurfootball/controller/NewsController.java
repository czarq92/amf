package org.amateurfootball.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.amateurfootball.model.News;
import org.amateurfootball.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NewsController {

	@Autowired
	private NewsRepository newsRepository;
	
	@RequestMapping(value = "/news")
	public String news(Model model){
		
		List<News> list = new ArrayList<>();
		
		list = newsRepository.findAll();
		Collections.reverse(list);
		
		model.addAttribute("news", list);
		
		return "news";
	}
}
