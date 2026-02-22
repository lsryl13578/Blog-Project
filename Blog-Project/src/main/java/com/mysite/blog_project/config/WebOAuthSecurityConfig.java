package com.mysite.blog_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mysite.blog_project.config.jwt.TokenProvider;
import com.mysite.blog_project.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.mysite.blog_project.config.oauth.OAuth2SuccessHandler;
import com.mysite.blog_project.config.oauth.OAuth2UserCustomService;
import com.mysite.blog_project.repository.RefreshTokenRepository;
import com.mysite.blog_project.service.UserDetailService;
import com.mysite.blog_project.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebOAuthSecurityConfig {
	
	private final OAuth2UserCustomService oAuth2UserCustomService;
	private final TokenProvider tokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;
	private final UserService userService;
	private final UserDetailService userDetailService;
	
	@Bean
	public WebSecurityCustomizer configure() {	// 스프링 시큐리티 기능 비활성화
		return (web) -> web.ignoring()
				.requestMatchers("/h2-console/**", "/img/**", "/css/**", "/js/**");
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.httpBasic(AbstractHttpConfigurer::disable)
				.formLogin(formLogin -> formLogin
						.loginPage("/login")
						.defaultSuccessUrl("/articles")
				)
				.userDetailsService(userDetailService)
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
						.sessionFixation().newSession()
				)
				// 헤더를 확인할 커스텀 필터 추가
				.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				// 토큰 재발급 URL은 인증 없이 접근 가능하도록 설정. 나머지 API URL은 인증 필요
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/token").permitAll()
						.requestMatchers("/api/**").authenticated()
						.requestMatchers("/login", "/signup").permitAll()
						.anyRequest().permitAll()
				)
				.oauth2Login(oauth2 -> oauth2
						.loginPage("/login")
				// Authorization 요청과 관련된 상태 저장
						.userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(oAuth2UserCustomService))
						// 인증 성공 시 실행할 핸들러
						.successHandler(oAuth2SuccessHandler())
				)
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/articles?logout=true")
						.invalidateHttpSession(true)
						.clearAuthentication(true)
						.deleteCookies("JSESSIONID", "access_token", "refresh_token", "oauth2_auth_request")
				)
				// /api로 시작하는 url인 경우 401 상태 코드를 반환하도록 예외 처리
				.exceptionHandling(exceptionHandling -> exceptionHandling
						.defaultAuthenticationEntryPointFor(
								new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
								request -> request.getRequestURI().startsWith("/api")
				))
				.build();
	}
	
	@Bean
	public OAuth2SuccessHandler oAuth2SuccessHandler() {
		return new OAuth2SuccessHandler(tokenProvider, refreshTokenRepository,
				oAuth2AuthorizationRequestBasedOnCookieRepository(), userService);
	}
	
	@Bean
	public TokenAuthenticationFilter tokenAuthenticationFilter() {
		return new TokenAuthenticationFilter(tokenProvider);
	}
	
	@Bean
	public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
		return new OAuth2AuthorizationRequestBasedOnCookieRepository();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}