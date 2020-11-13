package com.sbs.test.mysqltextboard.dto;

public class Member {
	public int id;
	public String memberId;
	public String password;
	public String name;

	public Member() {
	}

	public Member(int id, String memberId, String password, String name) {
		this.id = id;
		this.memberId = memberId;
		this.password = password;
		this.name = name;
	}
}
