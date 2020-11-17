package com.sbs.test.mysqltextboard.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.test.mysqltextboard.dto.Article;
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

	public Map<String, Object> getArticle(int index) {

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", index);

		Map<String, Object> article = MysqlUtil.selectRow(sql);

		return article;
	}

	public void delete(int index) {
		SecSql sql = new SecSql();
		sql.append("DELETE from article");
		sql.append("WHERE id = ?", index);

		MysqlUtil.delete(sql);
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

		MysqlUtil.update(sql);

		SecSql getter = new SecSql();
		getter.append("SELECT id");
		getter.append("FROM board");
		getter.append("ORDER BY id desc");
		getter.append("LIMIT 1");

		int id = MysqlUtil.selectRowIntValue(getter);

		return id;
	}

	public Map<String, Object> selectBoard(String id) {

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM board");
		sql.append("WHERE id = ?", id);

		Map<String, Object> board = MysqlUtil.selectRow(sql);

		if (board.size() == 0 || board.isEmpty()) {
			return null;
		}

		return board;
	}
}
