package com.mysite.blog_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.blog_project.domain.User;

// 사용자(User) 엔티티에 대한 데이터 접근을 담당하는 Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	// 이메일을 기준으로 사용자 정보를 조회 (로그인 시 사용)
	Optional<User> findByEmail(String email);
}