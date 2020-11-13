package com.sbs.test.mysqltextboard.contoller;

import java.util.List;
import java.util.Scanner;

import com.sbs.test.mysqltextboard.container.Container;
import com.sbs.test.mysqltextboard.dto.Article;
import com.sbs.test.mysqltextboard.dto.Member;
import com.sbs.test.mysqltextboard.service.ArticleService;
import com.sbs.test.mysqltextboard.service.MemberService;
import com.sbs.test.mysqltextboard.session.Session;

public class ArticleController extends Controller {

	private ArticleService articleService;
	private MemberService memberService;
	private Scanner sc;
	private Session session;

	public ArticleController() {
		articleService = Container.aritlceSerivce;
		memberService = Container.memberSerivce;
		sc = Container.scanner;
		session = Container.session;
	}

	public void doCmd(String cmd) {
		String code = cmd.split(" ")[1];

		if (code.equals("list")) {
			articleList();
		} else if (code.equals("write")) {
			articleAdd();
		} else if (code.equals("update")) {
			articleUpdate(cmd);
		} else if (code.equals("modify")) {
			articleModify(cmd);
		} else if (code.equals("detail")) {
			articleDetail(cmd);
		} else if (code.equals("delete")) {
			articleDelete(cmd);
		}
	}

	private void articleList() {
		System.out.println("== 게시물 리스트 ==");

		List<Article> articles = articleService.getArticles();

		System.out.println("id  /  작성자  /  내용  /  제목  /   작성시간");
		for (Article article : articles) {
			Member member = memberService.getMember(article.memberId);
			System.out.printf("%d  /  %s  /  %s  /  %s  /  %s\n", article.id, member.memberId, article.title,
					article.body, article.regDate);
		}
	}

	private void articleAdd() {
		System.out.println("== 게시물 등록 ==");

		if (!session.getLogined()) {
			System.out.println("로그인이 필요합니다.");
			return;
		}

		System.out.printf("title : ");
		String title = sc.nextLine();
		System.out.printf("body : ");
		String body = sc.nextLine();
		int memberId = session.getLoginUser().id;

		articleService.add(title, body, memberId);
	}

	private void articleUpdate(String cmd) {
		System.out.println("== 게시물 업데이트 ==");

		int index = Integer.parseInt(cmd.split(" ")[2]);
		articleService.update(index);
	}

	private void articleModify(String cmd) {
		System.out.println("== 게시물 수정 ==");

		int index = Integer.parseInt(cmd.split(" ")[2]);

		System.out.printf("title : ");
		String title = sc.nextLine();
		System.out.printf("body : ");
		String body = sc.nextLine();

		articleService.modify(index, title, body);
	}

	private void articleDetail(String cmd) {
		System.out.println("== 게시물 상세 ==");
		int index = Integer.parseInt(cmd.split(" ")[2]);
		Article article = articleService.getArticle(index);

		if (article == null) {
			System.out.println("존재하지 않는 게시물입니다.");
			return;
		}

		System.out.printf("id : %d\n", article.id);
		System.out.printf("작성시간 : %s\n", article.regDate);
		System.out.printf("수정시간 : %s\n", article.updatedate);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);
	}

	private void articleDelete(String cmd) {
		System.out.println("== 게시물 삭제 ==");

		int index = Integer.parseInt(cmd.split(" ")[2]);

		articleService.delete(index);
	}
}
