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
	
	private int start = 0;
	private int end = 5;
	private int listSize;
	
	@RequestMapping(value = "/news")
	public String news(Model model){
		List<News> list = new ArrayList<>();
		List<News> limitedList = new ArrayList<>();
		
		list = newsRepository.findAll();
		Collections.reverse(list);
		
		listSize = list.size();
		int counter = 0;
		
		start = 0;
		end = 5;
		
		for (News news : list) {
			counter++;
			
			if(counter >= start && counter <= end){
				limitedList.add(news);
			}
		}
		
		model.addAttribute("news", limitedList);
		
		return "news";
	}
	
	@RequestMapping(value = "/previous")
	public String previous(Model model){
		List<News> list = new ArrayList<>();
		List<News> limitedList = new ArrayList<>();
		
		list = newsRepository.findAll();
		Collections.reverse(list);
		
		listSize = list.size();
		
		int counter = 0;
		
		if(start > 5){
			start -= 5;
			
			if(end == listSize){
				end = start + 5;
			}else {
				end -=5;
			}
		} else{
			start = 0;
			end = 5;
		}
		
		for (News news : list) {
			counter++;
			
			if(counter > start && counter <= end){
				limitedList.add(news);
			}
		}
		
		model.addAttribute("news", limitedList);
		
		return "news";
	}
	
	@RequestMapping(value = "/next")
	public String next(Model model){
		List<News> list = new ArrayList<>();
		List<News> limitedList = new ArrayList<>();
		
		list = newsRepository.findAll();
		Collections.reverse(list);
		
		listSize = list.size();
		
		int counter = 0;
		
		if(end < listSize){
			start += 5;
			
			if( (end + 5) < listSize){
				end += 5;
			} else{
				end = listSize;
			}
		}
		
		for (News news : list) {
			counter++;
			
			if(counter > start && counter <= end){
				limitedList.add(news);
			}
		}
		
		model.addAttribute("news", limitedList);
		
		return "news";
	}
}
