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
	
	// 비밀번호 암호화를 위한 BCrypt 인코더
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	// 회원가입 요청을 처리
	// 비밀번호를 BCrypt로 암호화 한 후 사용자 정보를 저장
	public Long save(AddUserRequest dto) {
		return userRepository.save(User.builder()
				.email(dto.getEmail())
				.password(bCryptPasswordEncoder.encode(dto.getPassword()))
				.build()).getId();
	}
	
	public User findById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
	}
}