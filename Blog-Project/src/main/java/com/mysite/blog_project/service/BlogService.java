package com.mysite.blog_project.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.blog_project.domain.Article;
import com.mysite.blog_project.domain.User;
import com.mysite.blog_project.dto.AddArticleRequest;
import com.mysite.blog_project.dto.UpdateArticleRequest;
import com.mysite.blog_project.repository.BlogRepository;
import com.mysite.blog_project.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자를 추가한다.
@Service // 서블릿 컨테이너에 빈으로 등록, 서비스 역할의 클래스임을 명시한다.
public class BlogService {
	private final BlogRepository blogRepository;
	private final UserRepository userRepository;
	
	// 블로그 글 추가 메서드
	public Article save(AddArticleRequest request, String email) {
		// return blogRepository.save(request.toEntity(userName));
		
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("not found user"));
		
		Article article = request.toEntity();
		article.setUser(user);
		
		return blogRepository.save(article);
	}
	
	// 블로그 글 목록 조회 메서드
	public List<Article> findAll() {
		return blogRepository.findAll();
	}
	
	// 블로그 글 조회 메서드
	public Article findById(long id) {
		return blogRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("not found: " + id));
	}
	
	// 블로그 글 삭제 메서드
	public void delete(long id) {
		Article article = blogRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("not found : " + id));
		
		authorizeArticleAuthor(article);
		blogRepository.delete(article);
	}
	
	// 블로그 글 수정 메서드
	@Transactional
	public Article update(long id, UpdateArticleRequest request) {
		Article article = blogRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("not found: " + id));
		
		authorizeArticleAuthor(article);
		article.update(request.getTitle(), request.getContent());
		
		return article;
	}
	
	// 게시글을 작성한 유저인지 확인
	private static void authorizeArticleAuthor(Article article) {
		/*String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (!article.getAuthor().equals(userName)) {
			throw new IllegalArgumentException("not authorized");
		}*/
		
		String email = SecurityContextHolder.getContext()
				.getAuthentication()
				.getName();
		
		if (!article.getUser().getEmail().equals(email)) {
			throw new IllegalArgumentException("not authorized");
		}
	}
}