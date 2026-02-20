package com.mysite.blog_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
	
	// 로그인 페이지를 반환
	@GetMapping("/login")
	public String login() {
		return "oauthLogin";
	}
	
	// 회원가입 페이지를 반환
	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}
}