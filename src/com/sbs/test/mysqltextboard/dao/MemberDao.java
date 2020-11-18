package com.sbs.test.mysqltextboard.dao;

import java.util.Map;

import com.sbs.test.mysqltextboard.container.Container;
import com.sbs.test.mysqltextboard.dto.Member;
import com.sbs.test.mysqltextboard.mysqlutil.MysqlUtil;
import com.sbs.test.mysqltextboard.mysqlutil.SecSql;
import com.sbs.test.mysqltextboard.session.Session;

public class MemberDao {

	private Session session;

	public MemberDao() {
		session = Container.session;
	}

	public int join(String id, String pw, String name) {

		SecSql sql = new SecSql();
		sql.append("INSERT INTO `member` (memberId, `password`, `name`)");
		sql.append("VALUES (?, ?, ?)", id, pw, name);

		SecSql check = new SecSql();
		check.append("SELECT *");
		check.append("FROM member");
		check.append("WHERE memberId = ?", id);

		Map<String, Object> checker = MysqlUtil.selectRow(check);

		if (checker.size() != 0 || !checker.isEmpty()) {
			return -1;
		}

		int memberId = MysqlUtil.insert(sql);

		System.out.println(memberId);

		return memberId;
	}

	public Member login(String id, String pw) {
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM `member`");
		sql.append("WHERE memberId = ?", id);
		sql.append("AND password = ?", pw);

		Map<String, Object> map = MysqlUtil.selectRow(sql);
		Member member = new Member(map);

		return member;
	}

	public void logout() {
		session.setLogined(false);
		session.setLoginUser(null);
	}
}
