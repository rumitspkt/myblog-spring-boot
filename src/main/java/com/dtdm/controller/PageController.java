package com.dtdm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dtdm.service.PageService;

@Controller
public class PageController {
	
	@Autowired
	PageService pageService;
	
	@GetMapping("/about")
	public String showAbout(Model model) {
		model.addAttribute("pageAbout", pageService.findOne("About"));
		return "about";
	}
	
	@GetMapping("/event")
	public String showEvent(Model model) {
		model.addAttribute("pageEvent", pageService.findOne("Event"));
		return "event";
	}
}
