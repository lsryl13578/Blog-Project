package com.mysite.blog_project.service;

import org.springframework.stereotype.Service;

import com.mysite.blog_project.domain.Article;
import com.mysite.blog_project.dto.AddArticleRequest;
import com.mysite.blog_project.repository.BlogRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자를 추가한다.
@Service // 서블릿 컨테이너에 빈으로 등록, 서비스 역할의 클래스임을 명시한다.
public class BlogService {
	private final BlogRepository blogRepository;
	
	// 블로그 글 추가 메소드
	public Article save(AddArticleRequest request) {
		return blogRepository.save(request.toEntity());
	}
}