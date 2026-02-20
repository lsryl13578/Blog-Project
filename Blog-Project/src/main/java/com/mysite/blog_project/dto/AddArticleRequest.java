package com.mysite.blog_project.dto;

import com.mysite.blog_project.domain.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor // 기본 생성자를 추가한다.
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 추가한다.
@Getter
public class AddArticleRequest {
	private String title;
	private String content;
	
	public Article toEntity(String author) {
		return Article.builder()
				.title(title)
				.content(content)
				.author(author)
				.build();
	}
}