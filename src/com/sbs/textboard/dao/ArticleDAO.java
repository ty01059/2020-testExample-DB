package com.sbs.textboard.dao;

import java.util.ArrayList;
import java.util.List;

import com.sbs.textboard.dto.ArticleDTO;

public class ArticleDAO {

	private List<ArticleDTO> articles;

	public ArticleDAO() {
		articles = new ArrayList<ArticleDTO>();

		// test code
//		for (int i = 1; i <= 30; i++) {
//			ArticleDTO article = new ArticleDTO();
//			article.id = i;
//			article.title = "ar" + i;
//			article.body = "ar" + i;
//			article.regDate = "ar" + i;
//			article.writer = "ar" + i;
//
//			articles.add(article);
//		}
	}

	public int articleAdd(ArticleDTO article) {

		for (ArticleDTO newArticle : articles) {
			if (newArticle.id == article.id) {
				return -1;
			}
		}

		articles.add(article);
		return article.id;
	}

	public ArticleDTO getArticle(int index) {
		if (index > articles.size() - 1 || index < 0) {
			return null;
		}
		return articles.get(index);
	}

	public List<ArticleDTO> getArticles() {
		return articles;
	}
}