package com.cafe24.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.jblog.service.MainService;

@Controller
public class MainController {
	
	@Autowired
	MainService mainService;
	
	@RequestMapping({"/", "/main"})
	public String main() {
		return "main/index";
	}
	
	@RequestMapping("/main/search/blog")
	public String searchBlog(
			@RequestParam(value="keyword", required=true) String id) {
		
		// id 존재시 블로그 이동
		boolean result = mainService.searchBlog(id);
		
		if(result) {
			return "redirect:/" + id;
		}
		
		return "redirect:/main";
	}
}
