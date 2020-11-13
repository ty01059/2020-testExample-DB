package com.sbs.test.mysqltextboard.dto;

public class Article {

	public int id;
	public String regDate;
	public String updatedate;
	public String title;
	public String body;
	public int memberId;
	public int boardId;

	public Article() {

	}

	public Article(int id, String regDate, String updatedate, String title, String body, int memberId, int boardId) {
		this.id = id;
		this.regDate = regDate;
		this.updatedate = updatedate;
		this.title = title;
		this.body = body;
		this.memberId = memberId;
		this.boardId = boardId;
	}
}
