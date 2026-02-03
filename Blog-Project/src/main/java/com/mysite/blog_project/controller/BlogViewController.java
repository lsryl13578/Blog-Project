package com.mysite.blog_project.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.blog_project.domain.Article;
import com.mysite.blog_project.dto.ArticleListViewResponse;
import com.mysite.blog_project.dto.ArticleViewResponse;
import com.mysite.blog_project.service.BlogService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BlogViewController {
	
	private final BlogService blogService;
	
	// 블로그 글 목록 페이지 요청 처리 (전체 글 리스트 화면)
	@GetMapping("/articles")
	public String getArticles(Model model) {
		List<ArticleListViewResponse> articles = blogService.findAll().stream()
				.map(ArticleListViewResponse::new)
				.toList();
		model.addAttribute("articles", articles);
		
		return "articleList";
	}
	
	// 특정 블로그 글 상세 페이지 요청 처리
	@GetMapping("/articles/{id}")
	public String getArticle(@PathVariable("id") long id, Model model) {
		Article article = blogService.findById(id);
		model.addAttribute("article", new ArticleViewResponse(article));
		
		return "article";
	}
	
	// 블로그 글 작성 및 수정 페이지 요청 처리
	@GetMapping("/new-article")
	public String newArticle(@RequestParam(value = "id", required = false) Long id, Model model) {
		if (id == null) {
			model.addAttribute("article", new ArticleViewResponse());
		} else {
			Article article = blogService.findById(id);
			model.addAttribute("article", new ArticleViewResponse(article));
		}
		
		return "newArticle";
	}
}