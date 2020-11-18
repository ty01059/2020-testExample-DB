package com.sbs.test.mysqltextboard.dto;

import java.util.Map;

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

	public Article(Map<String, Object> map) {
		this.id = (int) map.get("id");
		this.regDate = (String) map.get("regDate");
		this.updatedate = (String) map.get("updatedate");
		this.title = (String) map.get("title");
		this.body = (String) map.get("body");
		this.memberId = (int) map.get("memberId");
		this.boardId = (int) map.get("boardId");
	}
}
