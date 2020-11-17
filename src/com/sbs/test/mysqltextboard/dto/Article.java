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

	public Article(Map<String, Object> maps) {
		this.id = (int) maps.get("id");
		this.regDate = (String) maps.get("regDate");
		this.updatedate = (String) maps.get("updatedate");
		this.title = (String) maps.get("title");
		this.body = (String) maps.get("body");
		this.memberId = (int) maps.get("memberId");
		this.boardId = (int) maps.get("boardId");
	}
}
