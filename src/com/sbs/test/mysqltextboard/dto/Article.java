package com.sbs.test.mysqltextboard.dto;

public class Article {

	public int id;
	public String regDate;
	public String title;
	public String body;
	public String updatedate;

	public Article() {

	}

	public Article(int id, String regDate, String title, String body, String updatedate) {
		this.id = id;
		this.regDate = regDate;
		this.title = title;
		this.body = body;
		this.updatedate = updatedate;
	}
}
