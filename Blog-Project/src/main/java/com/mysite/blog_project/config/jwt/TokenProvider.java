package com.mysite.blog_project.config.jwt;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.mysite.blog_project.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TokenProvider {
	
	private final JwtProperties jwtProperties;
	
	public String generateToken(User user, Duration expiredAt) {
		Date now = new Date();
		return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
	}
	
	// JWT 토큰 생성 메서드
	private String makeToken(Date expiry, User user) {
		Date now = new Date();
		
		SecretKey key = Keys.hmacShaKeyFor(
				jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
		
		return Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)	// 헤더 typ: JWT
				// 내용 iss: properties 파일에서 설정한 값
				.setIssuer(jwtProperties.getIssuer())
				.setIssuedAt(now)								// 내용 iat: 현재 시간
				.setExpiration(expiry)
				.setSubject(user.getEmail())
				.claim("id", user.getId())
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}
	
	// JWT 토큰 유효성 검증 메서드
	public boolean validToken(String token) {
		try {
			SecretKey key = Keys.hmacShaKeyFor(
					jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
			
			Jwts.parserBuilder()
				.setSigningKey(key)// 비밀값으로 복호화
				.build()
				.parseClaimsJws(token);
			
			return true;
		} catch (JwtException | IllegalArgumentException e) {	// 복호화 과정에서 에러가 나면 유효하지 않은 토큰
			// 서명 위조, 만료, 형식 오류 등
			return false;
		}
	}
	
	// 토큰 기반으로 인증 정보를 가져오는 메서드
	public Authentication getAuthentication(String token) {
		Claims claims = getClaims(token);
		Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
		
		return new UsernamePasswordAuthenticationToken(new org.springframework.
				security.core.userdetails.User(claims.getSubject(), "", authorities), token, authorities);
	}
	
	// 토큰 기반으로 유저 ID를 가져오는 메서드
	public Long getUserId(String token) {
		Claims claims = getClaims(token);
		return claims.get("id", Long.class);
	}
	
	private Claims getClaims(String token) {
		
		SecretKey key = Keys.hmacShaKeyFor(
				jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
		
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}