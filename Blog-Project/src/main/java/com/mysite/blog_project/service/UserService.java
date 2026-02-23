package com.mysite.blog_project.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.blog_project.domain.User;
import com.mysite.blog_project.dto.AddUserRequest;
import com.mysite.blog_project.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	// 사용자 정보에 대한 DB 접근을 담당하는 Repository
	private final UserRepository userRepository;
	
	
	// 회원가입 요청을 처리
	// 비밀번호를 BCrypt로 암호화 한 후 사용자 정보를 저장
	public Long save(AddUserRequest dto) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return userRepository.save(User.builder()
				.email(dto.getEmail())
				.password(encoder.encode(dto.getPassword()))
				.nickname(dto.getNickname())
				.build()).getId();
	}
	
	public User findById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
	}
	
	// 이메일을 입력받아 users 테이블에서 유저를 찾고, 없으면 예외 발생
	public User findByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
	}
}