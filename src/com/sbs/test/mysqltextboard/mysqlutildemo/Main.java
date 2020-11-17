package com.sbs.test.mysqltextboard.mysqlutildemo;

import java.util.List;
import java.util.Map;

import com.sbs.test.mysqltextboard.mysqlutil.MysqlUtil;
import com.sbs.test.mysqltextboard.mysqlutil.SecSql;

public class Main {
	public static void main(String[] args) {
		MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123", "a1");

		MysqlUtil.setDevMode(true);

		List<Map<String, Object>> articleListMap = MysqlUtil.selectRows(new SecSql().append("SELECT * FROM article"));
		System.out.println("articleListMap : " + articleListMap);

		Map<String, Object> articleMap = MysqlUtil
				.selectRow(new SecSql().append("SELECT * FROM article WHERE id = ?", 1));
		System.out.println("articleMap : " + articleMap);

		String title = MysqlUtil.selectRowStringValue(new SecSql().append("SELECT title FROM article WHERE id = ?", 1));
		System.out.println("title : " + title);

		int memberId = MysqlUtil.selectRowIntValue(new SecSql().append("SELECT memberId FROM article WHERE id = ?", 1));
		System.out.println("memberId : " + memberId);

		boolean memberIdIs1 = MysqlUtil
				.selectRowBooleanValue(new SecSql().append("SELECT memberId = 1 FROM article WHERE id = ?", 1));
		System.out.println("memberIdIs1 : " + memberIdIs1);

		MysqlUtil.closeConnection();
	}
}
