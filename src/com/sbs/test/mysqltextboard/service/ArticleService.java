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

	// ############ 게시판 #################
	public boolean getBoardWithName(String name) {
		return articleDao.getBoardWithName(name);
	}

	public boolean getBoardWithCode(String code) {
		return articleDao.getBoardWithCode(code);
	}

	public int makeBoard(String boardName, String code) {
		return articleDao.makeBoard(boardName, code);
	}

	public Board getBoard(String code) {
		return articleDao.getBoard(code);
	}

	public Board getBoard(int id) {
		return articleDao.getBoard(id);
	}

	public List<Board> getBoards() {
		return articleDao.getBoards();
	}

	// ############ 게시물 #################

	public int add(String title, String body, int memberId, int boardId) {
		return articleDao.add(title, body, memberId, boardId);
	}

	public int update(int index) {
		return articleDao.update(index);
	}

	public int modify(int index, String title, String body, int memberId) {
		return articleDao.modify(index, title, body, memberId);
	}

	public int delete(int index) {
		return articleDao.delete(index);
	}

	public void setArticleCount(int id, int count) {
		articleDao.setArticleCount(id, count);
	}

	public Article getArticle(int id) {
		return articleDao.getArticle(id);
	}

	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	public List<Article> getArticles(int id) {
		return articleDao.getArticles(id);
	}

	// ############ 댓글 #################
	public int writeReply(String body, int articleId, int memberId) {
		return articleDao.writeReply(body, articleId, memberId);
	}

	public List<Reply> getReply(int articleId) {
		return articleDao.getReply(articleId);
	}

	public int modifyReply(int id, String body, int memberId) {
		return articleDao.modifyReply(id, body, memberId);
	}

	public int deleteReply(int id, int memberId) {
		return articleDao.deleteReply(id, memberId);
	}
}
