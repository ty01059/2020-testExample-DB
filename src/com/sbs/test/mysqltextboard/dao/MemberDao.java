package com.sbs.test.mysqltextboard.dao;

import java.util.Map;

import com.sbs.test.mysqltextboard.container.Container;
import com.sbs.test.mysqltextboard.mysqlutil.MysqlUtil;
import com.sbs.test.mysqltextboard.mysqlutil.SecSql;
import com.sbs.test.mysqltextboard.session.Session;

public class MemberDao {

	private Session session;

	public MemberDao() {
		session = Container.session;
	}

	public Map<String, Object> getMember(int id) {
		SecSql sql = new SecSql();
		sql.append("select *");
		sql.append("from member");
		sql.append("where id = ?", id);

		Map<String, Object> member = MysqlUtil.selectRow(sql);

		return member;
	}

	public int join(String id, String pw, String name) {

		SecSql sql = new SecSql();
		sql.append("insert into `member`");
		sql.append("SET memberId = ?", id);
		sql.append("`password` = ?", pw);
		sql.append("`name` = ?", name);

		int memberId = MysqlUtil.insert(sql);

		return memberId;
	}

	public Map<String, Object> login(String id, String pw) {
		SecSql sql = new SecSql();
		sql.append("select *");
		sql.append("from `member`");
		sql.append("where memberId = ?", id);
		sql.append("and password = ?", pw);

		Map<String, Object> member = MysqlUtil.selectRow(sql);

		return member;
	}

	public void logout() {
		session.setLogined(false);
		session.setLoginUser(null);
	}
}
