package com.mysite.blog_project.dto;

import lombok.Getter;
import lombok.Setter;

// 회원가입 요청 시 전달되는 사용자 정보 DTO
@Getter
@Setter
public class AddUserRequest {
	
	// 사용자 이메일 (로그인 ID로 사용)
	private String email;
	
	// 사용자 비밀번호
	private String password;
	
	// 사용자 닉네임
	private String nickname;
}