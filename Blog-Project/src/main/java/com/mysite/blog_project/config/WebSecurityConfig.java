// OAuth2를 이용한 구글 로그인 기능 구현에 따라 임시로 전체 코드를 주석 처리함

/* package com.mysite.blog_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.mysite.blog_project.service.UserDetailService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	
	private final UserDetailService userService;
	
	// 스프링 시큐리티 기능 비활성화
	@Bean
	public WebSecurityCustomizer configure() {
		return (web) -> web.ignoring()
				.requestMatchers("/h2-console/**", "/static/**");
	}
	
	// 특정 HTTP 요청에 대한 웹 기반 보안 구성
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(auth -> auth	// 인증, 인가 설정
						.requestMatchers("/login", "/signup", "/user").permitAll()
						.anyRequest().authenticated())
				.formLogin(formLogin -> formLogin	// 폼 기반 로그인 설정
						.loginPage("/login")
						.defaultSuccessUrl("/articles")
				)
				.logout(logout -> logout			// 로그아웃 설정
						.logoutSuccessUrl("/login")
						.invalidateHttpSession(true)
				)
				.userDetailsService(userService)
				.csrf(AbstractHttpConfigurer::disable)	// csrf 비활성화
				.build();
	}
	
	// 패스워드 인코더로 사용할 빈 등록
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
} */