package com.sbs.test.mysqltextboard.service;

import java.util.List;

import com.sbs.test.mysqltextboard.container.Container;
import com.sbs.test.mysqltextboard.dao.ArticleDao;
import com.sbs.test.mysqltextboard.dto.Article;

public class ArticleService {
	private ArticleDao articleDao;

	public ArticleService() {
		articleDao = Container.articleDao;
	}

	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	public void add(String title, String body, int memberId) {
		articleDao.add(title, body, memberId);
	}

	public void update(int index) {
		articleDao.update(index);
	}

	public void modify(int index, String title, String body) {
		articleDao.modify(index, title, body);
	}

	public Article getArticle(int index) {
		return articleDao.getArticle(index);
	}

	public void delete(int index) {
		articleDao.delete(index);
	}
}
