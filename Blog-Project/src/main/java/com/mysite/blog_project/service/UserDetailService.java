package com.mysite.blog_project.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.mysite.blog_project.domain.User;
import com.mysite.blog_project.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	// 사용자 이름(email)으로 사용자의 정보를 가져오는 메서드
	@Override
	public User loadUserByUsername(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException((email)));
	}
}
