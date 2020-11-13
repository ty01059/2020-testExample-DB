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
	private List<Article> articles;

	public ArticleDao() {
		con = null;
		articles = new ArrayList<Article>();
	}

	public List<Article> getArticles() {

		String sql = "select * from article order by id desc";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/a1", "sbsst", "sbs123");
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Article article = new Article(rs.getInt("id"), rs.getString("regDate"), rs.getString("updatedate"),
						rs.getString("title"), rs.getString("body"), rs.getInt("memberId"), rs.getInt("boardId"));

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

	public void add(String title, String body, int memberId) {
		String sql = "insert into article ";
		sql += "SET regdate = NOW(), ";
		sql += "updatedate = NOW(), ";
		sql += "title = ?, ";
		sql += "`body` = ?, ";
		sql += "memberId = ?, ";
		sql += "boardId = ?";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/a1", "sbsst", "sbs123");
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, title);
			pstmt.setString(2, body);
			pstmt.setInt(3, memberId);
			pstmt.setInt(4, memberId);

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
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/a1", "sbsst", "sbs123");
			PreparedStatement pstmt = con.prepareStatement(sql);
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

	public void modify(int index, String title, String body, int memberId) {

		String sql = "update article ";
		sql += "SET title = ?, ";
		sql += "`body` = ?, ";
		sql += "updatedate = NOW() ";
		sql += "where id = ? and memberId = ?";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/a1", "sbsst", "sbs123");
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, title);
			pstmt.setString(2, body);
			pstmt.setInt(3, index);
			pstmt.setInt(4, memberId);

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
		sql += "where id = ?";

		Article article = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/a1", "sbsst", "sbs123");
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, index);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				article = new Article(rs.getInt("id"), rs.getString("regDate"), rs.getString("updatedate"),
						rs.getString("title"), rs.getString("body"), rs.getInt("memberId"), rs.getInt("boardId"));
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
		sql += "where id = ?";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/a1", "sbsst", "sbs123");
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, index);
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
