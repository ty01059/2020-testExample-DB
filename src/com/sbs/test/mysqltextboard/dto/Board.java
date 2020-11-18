package com.sbs.test.mysqltextboard.dto;

import java.util.Map;

public class Board {
	public int id;
	public String name;

	public Board() {
	}

	public Board(Map<String, Object> map) {
		this.id = (int) map.get("id");
		this.name = (String) map.get("name");
	}
}
