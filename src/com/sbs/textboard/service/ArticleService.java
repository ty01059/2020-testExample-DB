package com.sbs.textboard.service;

import java.util.List;

import com.sbs.textboard.dao.ArticleDAO;
import com.sbs.textboard.dto.ArticleDTO;

public class ArticleService {
	private ArticleDAO articleDAO;

	public ArticleService() {
		articleDAO = new ArticleDAO();
	}

	public int articleAdd(ArticleDTO article) {
		return articleDAO.articleAdd(article);
	}

	public ArticleDTO getArticle(int index) {
		return articleDAO.getArticle(index);
	}

	public List<ArticleDTO> getArticles() {
		return articleDAO.getArticles();
	}
}