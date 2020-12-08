package com.sbs.test.mysqltextboard.service;

import java.util.List;

import com.sbs.test.mysqltextboard.container.Container;
import com.sbs.test.mysqltextboard.dto.Article;
import com.sbs.test.mysqltextboard.dto.Board;
import com.sbs.test.mysqltextboard.fileUtil.Util;

public class BuildService {

	private ArticleService articleService;
//	private MemberService memberService;
	private Thread thread;
	private boolean buildSiteAuto;

	public BuildService() {
		articleService = Container.articleService;
//		memberService = Container.memberService;
		thread = new Thread();
	}

	public void buildSite() {
		Util.mkdirs("site/home");

		StringBuilder sb = new StringBuilder();
		List<Board> boards = articleService.getBoards();

		sb.append("<!DOCTYPE html>");
		sb.append("<html lang=\"ko\">");

		sb.append("<head>");
		sb.append("<meta charset=\"UTF-8\">");
		sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		sb.append("</head>");

		sb.append("<body>");

		sb.append("<h1>HOME</h1>");

		sb.append("<div>");

		sb.append("<a href=\"index.html\">HOME</a><br>");
		// board 테이블의 코드를 가져와서 code-$.html 생성하고
		for (Board board : boards) {
			sb.append("<a href=\"../article/" + board.code + "-list.html\">" + board.name + " 게시판</a><br>");

			articleListInBoard(board);
		}

		sb.append("</div>");

		sb.append("</body>");

		sb.append("</html>");

		String filePath = "site/home/index.html";

		Util.writeFile(filePath, sb.toString());
	}

	private void articleListInBoard(Board board) {
		System.out.println("site/article 폴더 생성");
		Util.mkdirs("site/article");

		StringBuilder sb = new StringBuilder();
		List<Article> articles = articleService.getArticles(board.id);

		sb.append("<!DOCTYPE html>");
		sb.append("<html lang=\"ko\">");

		sb.append("<head>");
		sb.append("<meta charset=\"UTF-8\">");
		sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		sb.append("</head>");

		sb.append("<body>");

		sb.append("<h1>" + board.name + " 게시판</h1>");

		sb.append("<div>");

		sb.append("<a href=\"../home/index.html\">HOME</a><br>");

		for (Article article : articles) {
			sb.append("<a href=\"../article/" + article.id + ".html\">" + article.id + "번 게시글</a><br>");

			articleDetailSite(article, articles);
		}

		sb.append("</div>");

		sb.append("</body>");

		sb.append("</html>");

		String filePath = "site/article/" + board.code + "-list.html";

		Util.writeFile(filePath, sb.toString());
	}

	private void articleDetailSite(Article article, List<Article> articles) {

		int nextArticleId = 0;
		int previousArticleId = 0;

		for (int i = 0; i < articles.size(); i++) {
			if (article.id == articles.get(i).id) {
				if (i < articles.size() - 1) {
					nextArticleId = articles.get(i + 1).id;
				}
				if (i > 0) {
					previousArticleId = articles.get(i - 1).id;
				}
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append("<!DOCTYPE html>");
		sb.append("<html lang=\"ko\">");

		sb.append("<head>");
		sb.append("<meta charset=\"UTF-8\">");
		sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		sb.append("<title>게시물 상세페이지 - " + article.title + "</title>");
		sb.append("</head>");

		sb.append("<body>");

		sb.append("<h1>게시물 상세페이지</h1>");

		sb.append("<div>");

		sb.append("번호 : " + article.id + "<br>");
		sb.append("작성날짜 : " + article.regDate + "<br>");
		sb.append("갱신날짜 : " + article.updateDate + "<br>");
		sb.append("제목 : " + article.title + "<br>");
		sb.append("내용 : " + article.body + "<br>");

		if (previousArticleId != 0) {
			sb.append("<a href=\"../article/" + previousArticleId + ".html\">이전글</a><br>");
		}

		if (nextArticleId != 0) {
			sb.append("<a href=\"../article/" + nextArticleId + ".html\">다음글</a><br>");
		}

		sb.append("<a href=\"../home/index.html\">HOME</a><br>");

		sb.append("</div>");

		sb.append("</body>");

		sb.append("</html>");

		String fileName = article.id + ".html";

		String filePath = "site/article/" + fileName;

		Util.writeFile(filePath, sb.toString());

		System.out.println(filePath + " 생성");
	}

	// 별도의 쓰레드를 켜서 build site 명령을 10초에 한번씩 수행한다. 즉 자동빌드켜기
	public void StartAutoSite() {
		buildSiteAuto = true;
//		thread
		new Thread(() -> {
			while (buildSiteAuto) {
				System.out.println(Thread.currentThread() + " : 작업");
//				buildSite();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
				}
			}
		}).start();
	}

	// 쓰레드를 끈다. 즉 자동빌드끄기
	public void StopAutoSite() {
		buildSiteAuto = false;
//		thread.interrupt();
	}
}
