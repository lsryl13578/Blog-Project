package com.mysite.blog_project.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Setter
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
	
	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;	// 게시글의 작성일 칼럼
	
	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;	// 게시글의 수정일 칼럼
	
	// 게시글 작성자(User)와 다대일 연관관계를 맺고, user_id 외래키로 지연 로딩한다.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Builder // 빌더 패턴으로 객체 생성
	public Article(String title, String content, String auth) {
		this.title = title;
		this.content = content;
	}
	
	// 엔티티에 요청받은 내용으로 값을 수정하는 메서드
	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}
}