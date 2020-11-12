package com.sbs.test.mysqltextboard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
			ResultSet result = prstmt.executeQuery();
			while (result.next()) {
				System.out.println(result.getObject("id"));
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
}
