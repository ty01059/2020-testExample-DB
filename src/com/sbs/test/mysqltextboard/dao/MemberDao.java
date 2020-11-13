package com.sbs.test.mysqltextboard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sbs.test.mysqltextboard.container.Container;
import com.sbs.test.mysqltextboard.dto.Member;
import com.sbs.test.mysqltextboard.session.Session;

public class MemberDao {

	private Session session;
	private Connection con;

	public MemberDao() {
		session = Container.session;
		con = null;
	}

	public Member getMember(int id) {
		Member member = null;

		String sql = "select * ";
		sql += "from `member` ";
		sql += "where id = ?";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/a1", "sbsst", "sbs123");
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				member = new Member(rs.getInt("id"), rs.getString("memberId"), rs.getString("password"),
						rs.getString("name"));
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

		return member;
	}

	public int join(String id, String pw, String name) {
		String sql = "insert into `member` ";
		sql += "SET memberId = ?, ";
		sql += "`password` = ?, ";
		sql += "`name` = ?";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/a1", "sbsst", "sbs123");
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);

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
		return 0;
	}

	public Member login(String id, String pw) {
		String sql = "select * ";
		sql += "from `member` ";
		sql += "where memberId = ? and password = ?";

		Member member = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/a1", "sbsst", "sbs123");
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, id);
			pstmt.setString(2, pw);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				member = new Member(rs.getInt("id"), rs.getString("memberId"), rs.getString("password"),
						rs.getString("name"));
				session.setLogined(true);
				session.setLoginUser(member);
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
		return member;
	}
}
