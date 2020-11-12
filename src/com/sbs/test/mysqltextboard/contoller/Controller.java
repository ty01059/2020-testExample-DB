package com.sbs.test.mysqltextboard.contoller;

import com.sbs.test.mysqltextboard.service.ArticleService;

public class Controller {

	private ArticleService articleService;

	public Controller() {
		articleService = new ArticleService();
	}

	public void doCmd() {
		System.out.println("== 게시물 리스트 ==");

		articleService.getList();
	}
}
