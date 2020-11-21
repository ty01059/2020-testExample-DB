package com.sbs.test.mysqltextboard.dto;

import java.util.Map;

public class Member {
	public int id;
	public String memberId;
	public String password;
	public String name;

	public Member() {
	}

	public Member(Map<String, Object> map) {
		this.id = (int) map.get("id");
		this.memberId = (String) map.get("memberId");
		this.password = (String) map.get("password");
		this.name = (String) map.get("name");
	}

	public String getType() {
		return isAdmin() ? "관리자" : "일반회원";
	}

	public boolean isAdmin() {
		return memberId.equals("aa");
	}
}
