package com.sbs.test.textboard.controller;

import java.util.List;
import java.util.Scanner;

import com.sbs.test.textboard.container.Container;
import com.sbs.test.textboard.dto.Article;
import com.sbs.test.textboard.service.ArticleService;

public class ArticleController extends Controller {

	private ArticleService articleService;
	private Scanner sc;

	private int count;
	private int maxCount;

	public ArticleController() {
		articleService = new ArticleService();
		sc = Container.scanner;

		count = 1;
		maxCount = 3;
	}

	public void doCommand(String command) {
		String code = command.split(" ")[1];

		if (code.equals("add")) {
			adrticleAdd(command);
		} else if (code.equals("list")) {
			adrticleList(command);
		}
	}

	private void adrticleAdd(String command) {
		System.out.println("== 게시물 등록 ==");

		if (!Container.session.getLogined()) {
			System.out.println("로그인이 필요합니다.");
			return;
		}

		while (true) {
			if (count > maxCount) {
				System.out.println("잠시후 다시 시도해주십시오.");
				break;
			}
			System.out.printf("제목 : ");
			String title = sc.nextLine().trim();

			if (title.equals("")) {
				System.out.println("제목을 입력해주세요.");
				count++;
				continue;
			}
			System.out.printf("내용 : ");
			String body = sc.nextLine();

			int id = articleService.articleAdd(title, body);
			System.out.printf("%s번 게시물이 등록되었습니다.\n", id);
			break;
		}
	}

	private void adrticleList(String command) {
		System.out.println("== 게시물 리스트 ==");

		List<Article> articles = articleService.getArticles();

		if (articles == null || articles.size() == 0) {
			System.out.println("등록된 게시물이 없습니다.");
			return;
		}

		System.out.println("번호  /  작성자  /  제목  /  내용  /  작성시간");
		for (int i = articles.size() - 1; i >= 0; i--) {
			Article article = articles.get(i);
			String writer = articleService.getWriter(article.writeNum);

			System.out.printf("%d  /  %s  /  %s  /  %s  /  %s\n", article.id, writer, article.title,
					article.body, article.regDate);
		}
	}
}
