package com.sbs.test.mysqltextboard.dto;

import java.util.Map;

public class Board {
	public int id;
	public String name;
	public String code;
	public int articleCount;

	public Board() {
	}

	public Board(Map<String, Object> map) {
		this.id = (int) map.get("id");
		this.name = (String) map.get("name");
		this.code = (String) map.get("code");
		if (map.get("articleCount") != null) {
			this.articleCount = (int) map.get("articleCount");
		}
	}
}
