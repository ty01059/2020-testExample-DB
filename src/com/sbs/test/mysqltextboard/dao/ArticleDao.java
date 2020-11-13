package com.sbs.test.mysqltextboard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sbs.test.mysqltextboard.dto.Article;

public class ArticleDao {

	private Connection con;
	private PreparedStatement pstmt;
	private List<Article> articles;

	public ArticleDao() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/a1", "sbsst", "sbs123");
		} catch (Exception e) {
			e.printStackTrace();
		}
		pstmt = null;
		articles = new ArrayList<Article>();
	}

	public List<Article> getList() {

		String sql = "select * from article order by id desc";

		try {
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Article article = new Article(rs.getInt("id"), rs.getString("regDate"), rs.getString("title"),
						rs.getString("body"), rs.getString("updatedate"));

				articles.add(article);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return articles;
	}

	public void add(String title, String body) {
		String sql = "insert into article ";
		sql += "SET regdate = NOW(), ";
		sql += "title = ?, ";
		sql += "`body` = ?, ";
		sql += "updatedate = NOW()";

		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, title);
			pstmt.setString(2, body);

			pstmt.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void update(int index) {
		String sql = "update article ";
		sql += "set updatedate = NOW() ";
		sql += "where id = " + index;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void modify(int index, String title, String body) {

		String sql = "update article ";
		sql += "SET title = ?, ";
		sql += "`body` = ?, ";
		sql += "updatedate = NOW() ";
		sql += "where id = " + index;

		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, title);
			pstmt.setString(2, body);

			pstmt.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Article getArticle(int index) {
		String sql = "select * from article ";
		sql += "where id = " + index;

		Article article = null;
		try {
			pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				article = new Article(rs.getInt("id"), rs.getString("regDate"), rs.getString("title"),
						rs.getString("body"), rs.getString("updatedate"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return article;
	}

	public void delete(int index) {

		String sql = "delete from article ";
		sql += "where id = " + index;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.execute();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
