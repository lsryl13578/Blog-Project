package com.mysite.blog_project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mysite.blog_project.dto.CreateAccessTokenRequest;
import com.mysite.blog_project.dto.CreateAccessTokenResponse;
import com.mysite.blog_project.service.TokenService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class TokenApiController {
	
	private final TokenService tokenService;
	
	@PostMapping("/api/token")
	public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken
	(@RequestBody CreateAccessTokenRequest request) {
		
		String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new CreateAccessTokenResponse(newAccessToken));
	}
}