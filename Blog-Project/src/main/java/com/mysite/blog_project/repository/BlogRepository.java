package com.mysite.blog_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.blog_project.domain.Article;

// 블로그 게시글(Article) 엔티티에 대한 데이터 접근을 담당하는 Repository
public interface BlogRepository extends JpaRepository<Article, Long> {
}