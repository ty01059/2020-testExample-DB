package com.sbs.test.mysqltextboard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sbs.test.mysqltextboard.Article;

public class ArticleDao {

	private Connection con;
	private List<Article> articles;

	public ArticleDao() {
		con = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}

		articles = new ArrayList<Article>();
	}

	public List<Article> getList() {

		String sql = "select * from article order by id desc";

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/a1", "sbsst", "sbs123");
			PreparedStatement prstmt = con.prepareStatement(sql);
			ResultSet rs = prstmt.executeQuery();

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
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/a1", "sbsst", "sbs123");
			PreparedStatement pstmt = con.prepareStatement(sql);

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
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/a1", "sbsst", "sbs123");
			PreparedStatement prstmt = con.prepareStatement(sql);
			prstmt.execute();

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
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/a1", "sbsst", "sbs123");
			PreparedStatement pstmt = con.prepareStatement(sql);

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
		Article article = new Article();

		String sql = "select * from article ";
		sql += "where id = " + index;

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/a1", "sbsst", "sbs123");
			PreparedStatement prstmt = con.prepareStatement(sql);
			ResultSet rs = prstmt.executeQuery();

			rs.next();
			article = new Article(rs.getInt("id"), rs.getString("regDate"), rs.getString("title"), rs.getString("body"),
					rs.getString("updatedate"));

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
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/a1", "sbsst", "sbs123");
			PreparedStatement prstmt = con.prepareStatement(sql);
			prstmt.execute();

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
