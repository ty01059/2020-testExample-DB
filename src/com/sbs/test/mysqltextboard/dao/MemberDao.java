package com.sbs.test.mysqltextboard.dao;

import java.util.Map;

import com.sbs.test.mysqltextboard.dto.Member;
import com.sbs.test.mysqltextboard.mysqlutil.MysqlUtil;
import com.sbs.test.mysqltextboard.mysqlutil.SecSql;

public class MemberDao {

	public MemberDao() {
	}

	public Member getMemberId(String memberId) {

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM `member`");
		sql.append("WHERE memberId = ?", memberId);

		Map<String, Object> map = MysqlUtil.selectRow(sql);
		if (!map.isEmpty()) {
			return new Member(map);
		}
		return null;
	}

	public int join(String memberId, String pw, String name) {

		SecSql sql = new SecSql();
		sql.append("INSERT INTO `member`(memberId, password, name)");
		sql.append("VALUES ( ?, ?, ?)", memberId, pw, name);

		int id = MysqlUtil.insert(sql);

		return id;
	}

	public Member login(String memberId, String pw) {

		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM `member`");
		sql.append("WHERE memberId = ? AND password = ?", memberId, pw);

		Map<String, Object> map = MysqlUtil.selectRow(sql);
		Member member = new Member(map);

		return member;
	}
}
