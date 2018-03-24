package com.dtdm.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dtdm.model.Admin;
import com.dtdm.model.Page;
import com.dtdm.model.Post;
import com.dtdm.service.AdminService;
import com.dtdm.service.PageService;
import com.dtdm.service.PostService;

@Controller
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@Autowired
	PageService pageService;
	
	@Autowired
	PostService postService;
	
	@GetMapping("/login")
	public String showLogIn(Model model) {
		return "login";
	}
	
	@GetMapping("/signup")
	public String showSignUp(Model model) {
		return "signup";
	}
	
	
	
	@GetMapping("/admin")
	public String showAdmin(Model model) {
		//check cookie 
		//Get username in Cookie
		return "admin";
	}
	
	@PostMapping("/authenticate")              //vd /admin/rum thi ko nhan Bootstrap ?????
	public String checkLogIn(@RequestParam("username") String username , @RequestParam("password") String password, RedirectAttributes redirect) {
		//authenticate
		Admin admin = adminService.findByUsername(username);
		if(admin == null || !admin.getPassword().equals(password)) {
			redirect.addFlashAttribute("failed", "Wrong username or password!!");
			return "redirect:/login";
		}
		//add Cookies
		redirect.addFlashAttribute("admin", admin);
		return "redirect:/admin";
	}
	
	@GetMapping("/formPage")
	public String showFormPage(Model model) {
		model.addAttribute("post", new Post());
		return "formPage";
	}
	
	@GetMapping("/formPost")
	public String showFormPost(Model model) {
		model.addAttribute("post", new Post());
		return "formPost";
	}
	
	@PostMapping("/formPostSave")
	public String saveFormPost(@Valid Post post, BindingResult result, RedirectAttributes redirect) {
		if(result.hasErrors()) {
			return "formPost";
		}
		postService.save(post);
		redirect.addFlashAttribute("success", "Saved post successfully!");
		redirect.addFlashAttribute("admin", new Admin(1, "rum", "123"));
		return "redirect:/admin";
	}
	
	@GetMapping("/post/{id}/edit")
	public String editPost(@PathVariable int id, Model model) {
		model.addAttribute("post", postService.findOne(id));
		return "formPost";
	}
	
	@GetMapping("/post/{id}/delete")
	public String deletePost(@PathVariable int id, RedirectAttributes redirect) {
		postService.delete(id);
		redirect.addFlashAttribute("admin", new Admin(1, "rum", "123"));
		redirect.addFlashAttribute("success", "Deleted post successfully!");
		return "redirect:/admin";
	}
	
	@GetMapping("/listPost")
	public String showListPost(Model model) {
		model.addAttribute("posts", postService.findAll());
		return "list";
	}
	
	@PostMapping("/createPost")              
	public String createPost(@Valid Post post, BindingResult result, RedirectAttributes redirect) {
		//authenticate
		return "admin";
	}
	
	//code tao lao
	@GetMapping("/formPageEvent")
	public String showFormPageEvent(Model model) {
		model.addAttribute("page", pageService.findOne("Event"));
		return "formPage";
	}
	
	@GetMapping("/formPageAbout")
	public String showFormPageAbout(Model model) {
		model.addAttribute("page", pageService.findOne("About"));
		return "formPage";
	}
	@PostMapping("/formPageSave")
	public String savePage(RedirectAttributes redirect, @RequestParam("header") String header, @RequestParam("title") String title, @RequestParam("content") String content) {
		pageService.save(new Page(header, title, content));
		redirect.addFlashAttribute("success", "Update page completely!!");
		redirect.addFlashAttribute("admin", new Admin(1, "rum", "123"));
		return "redirect:/admin";
	}
	
	
	//test
	@PostMapping("/test")
	public String test(@RequestParam("content") String content) {
		System.out.println(content);
		return "signup";
	}
	
	
}
