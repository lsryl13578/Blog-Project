package com.mysite.blog_project.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.mysite.blog_project.dto.AddUserRequest;
import com.mysite.blog_project.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserApiController {

	private final UserService userService;
	
	// 회원가입 요청을 처리
	// 전달받은 사용자 요청을 저장한 뒤 로그인 페이지로 리다이렉트
	@PostMapping("/user")
	public String signup(AddUserRequest request) {
		userService.save(request);
		return "redirect:/login";
	}
	
	// 로그아웃 요청을 처리
	// 현재 인증된 사용자 정보를 SecurityContext에서 제거하고 로그인 페이지로 이동
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
		return "redirect:/login";
	}
}
