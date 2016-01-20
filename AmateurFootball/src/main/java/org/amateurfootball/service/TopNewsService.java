package org.amateurfootball.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.amateurfootball.model.News;
import org.amateurfootball.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopNewsService {
	
	@Autowired
	private NewsRepository newsRepository;
	private List<News> topNewsList;
	
	public List<News> getTopNewsList(){
		int counter, sizeList;
		
		counter = 0;
		sizeList = newsRepository.findAll().size();
		
		topNewsList = new ArrayList<>();
		
		for (News news : newsRepository.findAll()) {
			if(counter >= sizeList-3){
				topNewsList.add(news);
			}
			++counter;
		}
		
		Collections.reverse(topNewsList);
		
		return topNewsList;
	}
}
