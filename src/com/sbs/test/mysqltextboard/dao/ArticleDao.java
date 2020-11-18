package com.sbs.test.mysqltextboard.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.test.mysqltextboard.dto.Article;
import com.sbs.test.mysqltextboard.dto.Board;
import com.sbs.test.mysqltextboard.dto.Reply;
import com.sbs.test.mysqltextboard.mysqlutil.MysqlUtil;
import com.sbs.test.mysqltextboard.mysqlutil.SecSql;

public class ArticleDao {

	private List<Article> articles;

	public ArticleDao() {
		articles = new ArrayList<>();
	}

	public List<Article> getArticles() {

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("ORDER BY id desc");

		List<Map<String, Object>> maps = MysqlUtil.selectRows(sql);

		for (Map<String, Object> map : maps) {
			Article article = new Article(map);
			articles.add(article);
		}

		return articles;
	}

	public List<Article> getArticles(int boardId) {

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE boardId = ?", boardId);
		sql.append("ORDER BY id desc");

		List<Map<String, Object>> maps = MysqlUtil.selectRows(sql);

		for (Map<String, Object> map : maps) {
			Article article = new Article(map);
			articles.add(article);
		}

		return articles;
	}

	public int add(String title, String body, int memberId, int boardId) {

		SecSql sql = new SecSql();
		sql.append("INSERT into article (regdate, updatedate, title, `body`, memberId, boardId)");
		sql.append("VALUES (NOW(), NOW(), ?, ?, ?, ?)", title, body, memberId, boardId);

		int id = MysqlUtil.insert(sql);
		return id;
	}

	public int update(int index) {

		SecSql sql = new SecSql();
		sql.append("UPDATE article");
		sql.append("SET updatedate = NOW()");
		sql.append("WHERE id = ?", index);

		int result = MysqlUtil.update(sql);

		return result;
	}

	public int modify(int index, String title, String body, int memberId) {

		SecSql sql = new SecSql();
		sql.append("UPDATE article");
		sql.append("SET title = ?,", title);
		sql.append("`body` = ?,", body);
		sql.append("updatedate = NOW()");
		sql.append("WHERE id = ? and memberId = ?", index, memberId);

		int result = MysqlUtil.update(sql);
		return result;
	}

	public Article getArticle(int index) {

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", index);

		Map<String, Object> map = MysqlUtil.selectRow(sql);

		Article article = new Article(map);

		return article;
	}

	public int delete(int index) {
		SecSql sql = new SecSql();
		sql.append("DELETE from article");
		sql.append("WHERE id = ?", index);

		int id = MysqlUtil.delete(sql);
		return id;
	}

	public int createBoard(String boardName) {

		SecSql check = new SecSql();
		check.append("SELECT name");
		check.append("FROM board");
		check.append("WHERE name = ?", boardName);

		Map<String, Object> checker = MysqlUtil.selectRow(check);

		if (checker.size() != 0 || !checker.isEmpty()) {
			return -1;
		}

		SecSql sql = new SecSql();
		sql.append("INSERT INTO board (name)");
		sql.append("values ( ? )", boardName);

		int id = MysqlUtil.update(sql);

		return id;
	}

	public Board selectBoard(int id) {

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM board");
		sql.append("WHERE id = ?", id);

		Map<String, Object> map = MysqlUtil.selectRow(sql);

		if (map.size() == 0 || map.isEmpty()) {
			return null;
		}

		Board board = new Board(map);

		return board;
	}

	public int writeReply(String body, int articleId, int memberId) {

		SecSql sql = new SecSql();
		sql.append("INSERT INTO articleReply (regDate, body, articleId, memberId)");
		sql.append("values ( NOW(), ?, ?, ? )", body, articleId, memberId);

		int id = MysqlUtil.insert(sql);

		return id;
	}

	public List<Reply> getReply(int articleId) {

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM articleReply");
		sql.append("WHERE articleId = ?", articleId);

		List<Map<String, Object>> maps = MysqlUtil.selectRows(sql);

		List<Reply> replys = new ArrayList<>();
		for (Map<String, Object> map : maps) {
			Reply reply = new Reply();
			reply.id = (int) map.get("id");
			reply.regDate = (String) map.get("regDate");
			reply.body = (String) map.get("body");
			reply.articleId = (int) map.get("articleId");
			reply.memberId = (int) map.get("memberId");

			replys.add(reply);
		}
		return replys;
	}
}
