package com.mysite.blog_project.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {
	@Id // id 필드를 기본키로 지정한다.
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩 증가한다.
	@Column(name = "id", updatable = false)
	private Long id;
	
	@Column(name = "title", nullable = false) // 게시글의 제목 칼럼
	private String title;
	
	@Column(name = "content", nullable = false) // 게시글의 내용 칼럼
	private String content;
	
	@Builder // 빌더 패턴으로 객체 생성
	public Article(String title, String content) {
		this.title = title;
		this.content = content;
	}
}