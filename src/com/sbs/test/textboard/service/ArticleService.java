package com.sbs.test.textboard.service;

import java.util.List;

import com.sbs.test.textboard.container.Container;
import com.sbs.test.textboard.dao.ArticleDao;
import com.sbs.test.textboard.dto.Article;

public class ArticleService {

	private ArticleDao articleDao;

	public ArticleService() {
		articleDao = Container.articleDao;
	}

	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	public int articleAdd(String title, String body) {
		return articleDao.articleAdd(title, body);
	}

	public String getWriter(int writeNum) {
		return articleDao.getWriter(writeNum);
	}

}
