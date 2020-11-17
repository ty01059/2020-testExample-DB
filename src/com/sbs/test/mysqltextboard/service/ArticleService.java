package com.sbs.test.mysqltextboard.service;

import java.util.List;
import java.util.Map;

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

	public int add(String title, String body, int memberId, int boardId) {
		return articleDao.add(title, body, memberId, boardId);
	}

	public int update(int index) {
		return articleDao.update(index);
	}

	public int modify(int index, String title, String body, int memberId) {
		return articleDao.modify(index, title, body, memberId);
	}

	public Map<String, Object> getArticle(int index) {
		return articleDao.getArticle(index);
	}

	public void delete(int index) {
		articleDao.delete(index);
	}

	public int createBoard(String boardName) {
		return articleDao.createBoard(boardName);
	}

	public Map<String, Object> selectBoard(String id) {
		return articleDao.selectBoard(id);		
	}
}
