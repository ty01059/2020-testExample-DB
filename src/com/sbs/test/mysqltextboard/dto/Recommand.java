package com.sbs.test.mysqltextboard.dto;

import java.util.Map;

public class Recommand {
	public int id;
	public String regDate;
	public String updateDate;
	public int memberId;
	public int articleId;

	public Recommand(Map<String, Object> map) {
		this.id = (int) map.get("id");
		this.regDate = (String) map.get("regDate");
		this.updateDate = (String) map.get("updateDate");
		this.memberId = (int) map.get("memberId");
		this.articleId = (int) map.get("articleId");
	}
}
