package com.mysite.blog_project.util;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	// 요청값 (이름, 값, 만료 기간)을 바탕으로 쿠키 추가
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setHttpOnly(true);	// 보안 강화
		cookie.setMaxAge(maxAge);
		
		response.addCookie(cookie);
	}
	
	// 쿠키 조회
	public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
		
		if (request.getCookies() == null) {
			return Optional.empty();
		}
		
		for (Cookie cookie : request.getCookies()) {
			if (name.equals(cookie.getName())) {
				return Optional.of(cookie);
			}
		}
		
		return Optional.empty();
	}
	
	// 쿠키의 이름을 입력받아 쿠키 삭제
	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		Cookie[] cookies = request.getCookies();
		
		if (cookies == null) {
			return;
		}
		
		for (Cookie cookie : cookies) {
			if (name.equals(cookie.getName())) {
				cookie.setValue("");
				cookie.setPath("/");
				cookie.setMaxAge(0);
				
				response.addCookie(cookie);
			}
		}
	}
	
	// 객체를 직렬화해 쿠키의 값으로 변환
	public static String serialize(Object obj) {
		try {
			byte[] jsonBytes = objectMapper.writeValueAsBytes(obj);
			
			return Base64.getUrlEncoder()
					.encodeToString(jsonBytes);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Failed to serialize object", e);
		}
	}
	
	// 쿠키를 역직렬화해 객체로 변환
	public static <T> T deserialize(Cookie cookie, Class<T> cls) {
		try {
			byte[] decodedBytes = Base64.getUrlDecoder()
					.decode(cookie.getValue());
			
			return objectMapper.readValue(decodedBytes, cls);
		} catch (IOException e) {
			throw new IllegalArgumentException("Failed to deserialize cookie", e);
		}
	}
}