package com.mysite.blog_project.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mysite.blog_project.domain.Article;
import com.mysite.blog_project.dto.AddArticleRequest;
import com.mysite.blog_project.dto.ArticleResponse;
import com.mysite.blog_project.dto.UpdateArticleRequest;
import com.mysite.blog_project.service.BlogService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController // Http Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class BlogApiController {
	private final BlogService blogService;
	
	// HTTP 메소드가 POST일 때 전달받은 URL과 동일하면 메서드로 매핑
	@PostMapping("/api/articles")
	// @RequestBody 어노테이션을 이용해 본문 값 매핑
	public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
		Article savedArticle = blogService.save(request);
		
		// 요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(savedArticle);
	}
	
	// 전체 게시글 목록을 조회하는 GET API 요청을 처리
	@GetMapping("/api/articles")
	public ResponseEntity<List<ArticleResponse>> findAllArticles() {
		
		// 서비스 계층에서 전체 Article 엔티티 목록을 조회
		List<ArticleResponse> articles = blogService.findAll()
				// 조회된 Article 엔티티들을 ArticleResponse DTO로 변환
				.stream()
				.map(ArticleResponse::new)
				
				// 변환된 결과를 List로 수집
				.toList();
		
		// HTTP 상태 코드 200(OK)와 함께 게시글 목록을 JSON 형태로 응답
		return ResponseEntity.ok()
				.body(articles);
	}
	
	// 특정 게시글을 조회하는 GET API 요청을 처리
	@GetMapping("/api/articles/{id}")
	// URL 경로에서 값 추출
	public ResponseEntity<ArticleResponse> findArticle(@PathVariable("id") long id) {
		
		Article article = blogService.findById(id);
		
		return ResponseEntity.ok()
				.body(new ArticleResponse(article));
	}
	
	// 특정 게시글을 삭제하는 DELETE API 요청을 처리
	@DeleteMapping("/api/articles/{id}")
	public ResponseEntity<Void> deleteArticle(@PathVariable("id") long id) {
		blogService.delete(id);
		
		return ResponseEntity.ok()
				.build();
	}
	
	// 특정 게시글을 수정하는 PUT API 요청을 처리
	@PutMapping("/api/articles/{id}")
	public ResponseEntity<Article> updateArticle(@PathVariable("id") long id, @RequestBody UpdateArticleRequest request) {
		Article updatedArticle = blogService.update(id, request);
		
		return ResponseEntity.ok()
				.body(updatedArticle);
	}
}