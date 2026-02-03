package com.mysite.blog_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.blog_project.domain.Article;

public interface BlogRepository extends JpaRepository<Article, Long> {
}