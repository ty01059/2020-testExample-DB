package com.sbs.textboard.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.sbs.textboard.container.Container;
import com.sbs.textboard.dto.ArticleDTO;
import com.sbs.textboard.dto.MemberDTO;
import com.sbs.textboard.service.ArticleService;
import com.sbs.textboard.service.MemberService;
import com.sbs.textboard.session.Session;

public class ArticleController extends Controller {
	private ArticleService articleService;
	private MemberService memberService;
	private Session session;
	private Scanner sc;
	private int articleId;
	private List<ArticleDTO> articles;

	public ArticleController() {
		articleService = Container.articleService;
		memberService = Container.memberService;
		session = Container.session;
		sc = Container.scanner;
		articleId = 0;
		articles = new ArrayList<ArticleDTO>();
	}

	private String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return format.format(date);
	}

	public void doCommand(String command) {

		if (command.equals("article add")) {
			add(command);
		} else if (command.startsWith("article list")) {
			list(command);
		}
	}

	private void add(String command) {
		if (!session.getIsLogined()) {
			System.out.println("로그인이 필요합니다.");
			return;
		}

		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		ArticleDTO newArticle = new ArticleDTO();
		newArticle.id = articleId + 1;
		newArticle.title = title;
		newArticle.body = body;
		newArticle.regDate = getDate();
		newArticle.writerNum = session.getloginUser().id;

		int result = articleService.articleAdd(newArticle);

		if (result == -1) {
			System.out.println("더미데이터 오류");
			return;
		}
		System.out.printf("%d번 게시물이 등록되었습니다.\n", result);
		articleId++;
	}

	private void list(String command) {
		articles = articleService.getArticles();

		if (articles == null || articles.size() == 0) {
			System.out.println("등록된 게시물이 없습니다.");
		}

		System.out.println("번호  /  작성자  /  제목  /  내용  /  작성시간 ");

		if (command.split(" ").length == 2) {
			for (int i = articles.size() - 1; i >= 0; i--) {
				printList(i);
			}
		} else if (command.split(" ").length == 3) {
			int inputIndex = Integer.parseInt(command.split(" ")[2]);
			int pageInarticle = 10;
			int startArticle = articles.size() - (pageInarticle * (inputIndex - 1));

			for (int i = startArticle - 1; i >= startArticle - pageInarticle + 1; i--) {
				if (i >= 0) {
					printList(i);
				}
			}
		}
	}

	private void printList(int i) {
		ArticleDTO article = articleService.getArticle(i);

		String writer = "";
		for (MemberDTO member : memberService.getMembers()) {
			if (member.id == article.writerNum) {
				writer = member.memberId;
			}
		}

		System.out.printf("%d  /  %s  /  %s  /  %s  /  %s \n", article.id, writer, article.title, article.body,
				article.regDate);
	}
}