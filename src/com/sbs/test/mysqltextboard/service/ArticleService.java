package com.sbs.test.mysqltextboard.service;

import java.util.List;

import com.sbs.test.mysqltextboard.container.Container;
import com.sbs.test.mysqltextboard.dao.ArticleDao;
import com.sbs.test.mysqltextboard.dto.Article;
import com.sbs.test.mysqltextboard.dto.Board;
import com.sbs.test.mysqltextboard.dto.Reply;

public class ArticleService {
	private ArticleDao articleDao;

	public ArticleService() {
		articleDao = Container.articleDao;
	}

	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	public List<Article> getArticles(int id) {
		return articleDao.getArticles(id);
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

	public Article getArticle(int index) {
		return articleDao.getArticle(index);
	}

	public int delete(int index) {
		return articleDao.delete(index);
	}

	public int createBoard(String boardName) {
		return articleDao.createBoard(boardName);
	}

	public Board selectBoard(int id) {
		return articleDao.selectBoard(id);
	}

	public int writeReply(String body, int articleId, int memberId) {
		return articleDao.writeReply(body, articleId, memberId);
	}

	public List<Reply> getReply(int articleId) {
		return articleDao.getReply(articleId);
	}
}
