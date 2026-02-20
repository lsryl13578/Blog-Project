package com.mysite.blog_project.dto;

import java.time.LocalDateTime;

import com.mysite.blog_project.domain.Article;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ArticleViewResponse {
	
	private Long id;
	private String title;
	private String content;
	private LocalDateTime createdAt;
	private String author;
	
	public ArticleViewResponse(Article article) {
		this.id = article.getId();
		this.title = article.getTitle();
		this.content = article.getContent();
		this.createdAt = article.getCreatedAt();
		this.author = article.getAuthor();
	}
}