package com.sbs.test.mysqltextboard.service;

import java.util.List;

import com.sbs.test.mysqltextboard.container.Container;
import com.sbs.test.mysqltextboard.dto.Article;
import com.sbs.test.mysqltextboard.dto.Board;
import com.sbs.test.mysqltextboard.fileUtil.Util;

public class BuildService {

	private ArticleService articleService;
	private boolean buildSiteAuto;

	private String foot = Util.getFileContents("site_template/foot.html");

	public BuildService() {
		articleService = Container.articleService;
	}

	public void buildSite() {

		Util.rmdir("site");
		Util.mkdirs("site");
		Util.copy("site_template/app.css", "site/app.css");

		StringBuilder sb = new StringBuilder();
		String head = getHeadHtml("home");
		String body = Util.getFileContents("site_template/home_body.html");

		sb.append(head);
		sb.append(body);
		sb.append(foot);

		String filePath = "site/index.html";

		Util.writeFile(filePath, sb.toString());
	}

	private void articleListInBoard(Board board) {
		List<Article> articles = articleService.getArticles(board.id);
		String head = getHeadHtml("board");
		int itemsInAPage = 10;
		int articleCount = articles.size();
		int totalPage = (int) Math.ceil((double) articleCount / itemsInAPage);

		for (int i = 1; i <= totalPage; i++) {
			StringBuilder sb = new StringBuilder();
			StringBuilder articleListAndPage = new StringBuilder();
			String body = Util.getFileContents("site_template/article_list_body.html");

			int startList = (i - 1) * itemsInAPage;
			int end = startList + itemsInAPage - 1;
			if (end >= articleCount) {
				end = articleCount - 1;
			}

			for (int j = startList; j <= end; j++) {
				articleListAndPage.append("<div class=\"flex\">");
				articleListAndPage.append("<div class=\"article-list__cell-id\">" + articles.get(j).id + "</div>");
				articleListAndPage
						.append("<div class=\"article-list__cell-reg-date\">" + articles.get(j).regDate + "</div>");
				articleListAndPage
						.append("<div class=\"article-list__cell-writer\">" + articles.get(j).writer + "</div>");
				articleListAndPage.append("<div class=\"article-list__cell-title\">");
				articleListAndPage.append("<a href=\"" + articles.get(j).id
						+ ".html\" class=\"hover-underline\">" + articles.get(j).title + "</a>");
				articleListAndPage.append("</div>");
				articleListAndPage.append("</div>");
				articleDetailSite(articles.get(j), articles, i);
			}

			// page
			if (i < 1) {
				i = 1;
			}

			if (i > totalPage) {
				i = totalPage;
			}

			// 현재 페이지 시작, 끝 박스
			int maxPageCount = 10;
			int prevPageBoxCount = (i - 1) / maxPageCount;
			int pageBoxStartPage = maxPageCount * prevPageBoxCount + 1;
			int pageBoxEndPage = pageBoxStartPage + maxPageCount - 1;

			if (pageBoxEndPage > totalPage) {
				pageBoxEndPage = totalPage;
			}

			// 이전버튼 페이지 계산
			int pageBoxStartBeforePage = pageBoxStartPage - 1;
			if (pageBoxStartBeforePage < 1) {
				pageBoxStartBeforePage = 1;
			}

			// 다음버튼 페이지 계산
			int pageBoxEndAfterPage = pageBoxEndPage + 1;

			if (pageBoxEndAfterPage > totalPage) {
				pageBoxEndAfterPage = totalPage;
			}

			// 이전버튼 노출여부 계산
			boolean pageBoxStartBeforeBtnNeedToShow = pageBoxStartBeforePage != pageBoxStartPage;
			// 다음버튼 노출여부 계산
			boolean pageBoxEndAfterBtnNeedToShow = pageBoxEndAfterPage != pageBoxEndPage;

			articleListAndPage.append("<div class=\"article-page-menu\">");
			articleListAndPage.append("<ul class=\"flex flex-jc-c\">");
			if (pageBoxStartBeforeBtnNeedToShow) {
				articleListAndPage.append("<li><a href=\"" + board.code + "-list_" + pageBoxStartBeforePage
						+ ".html\" class=\"flex flex-ai-c\"> &lt; 이전</a></li>");
			}
			for (int j = pageBoxStartPage; j <= pageBoxEndPage; j++) {
				String selectedClass = "";
				if (j == i) {
					selectedClass = "article-page-menu__link--selected";
				}

				articleListAndPage.append("<li><a href=\"" + board.code + "-list_" + j
						+ ".html\" class=\"flex flex-ai-c " + selectedClass + "\">" + j + " </a></li>");
			}
			if (pageBoxEndAfterBtnNeedToShow) {
				articleListAndPage.append("<li><a href=\"" + board.code + "-list_" + pageBoxEndAfterPage
						+ ".html\" class=\"flex flex-ai-c\">다음 &gt;</a></li>");
			}
			body = body.replace("${article_list_content-page}", articleListAndPage);
			body = body.replace("${board_code}", board.code.toUpperCase());

			sb.append(head);
			sb.append(body);
			sb.append(foot);

			String filePath = "site/" + board.code + "-list_" + i + ".html";

			Util.writeFile(filePath, sb.toString());
		}
	}

	private void articleDetailSite(Article article, List<Article> articles, int page) {

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

		Board board = articleService.getBoard(article.boardId);

		StringBuilder sb = new StringBuilder();
		StringBuilder articleDetail = new StringBuilder();
		String head = getHeadHtml("detail");
		String body = Util.getFileContents("site_template/article_detail_body.html");

		articleDetail.append("<div class=\"title\">");
		articleDetail.append("<div>[" + board.name + "]</div>");
		articleDetail.append("<div>" + article.title + "</div>");
		articleDetail.append("</div>");
		articleDetail.append("<div class=\"writer\">");
		articleDetail.append("<div><i class=\"fas fa-user-edit\"></i>&nbsp;" + article.writer + "</div>");
		articleDetail.append("<div>" + article.regDate + "</div>");
		articleDetail.append("<div><i class=\"fas fa-eye\"></i>&nbsp;" + article.view + "</div>");
		articleDetail.append("</div>");
		articleDetail.append("<div class=\"body\">" + article.body + "</div>");
		articleDetail.append("</section>");

		articleDetail.append("<section class=\"button\">");
		articleDetail.append("<div class=\"flex\">");

		String previousdisable = "";
		String nextdisable = "";
		if (previousArticleId == 0) {
			previousdisable = " class=\"a-pointer-events-none\"";
		}
		if (nextArticleId == 0) {
			nextdisable = " class=\"a-pointer-events-none\"";
		}

		articleDetail
				.append("<a href=\"" + previousArticleId + ".html\"" + previousdisable + ">이전글</a><br>");
		articleDetail.append("<a href=\"" + board.code + "-list_" + page + ".html\">목록</a><br>");
		articleDetail.append("<a href=\"" + nextArticleId + ".html\"" + nextdisable + ">다음글</a><br>");
		articleDetail.append("</div>");
		articleDetail.append("</section>");

		body = body.replace("${article_detail_info}", articleDetail);

		sb.append(head);
		sb.append(body);
		sb.append(foot);

		String fileName = article.id + ".html";

		String filePath = "site/" + fileName;

		Util.writeFile(filePath, sb.toString());

		System.out.println(filePath + " 생성");
	}

	private String getHeadHtml(String keyword) {
		String head = Util.getFileContents("site_template/head.html");
		List<Board> boards = articleService.getBoards();
		StringBuilder boardList = new StringBuilder();

		for (Board board : boards) {
			boardList.append("<li>");
			boardList.append("<a href=\"" + board.code + "-list_1.html\" class=\"block\">");
			if (board.code.equals("free")) {
				boardList.append("<i class=\"fab fa-free-code-camp\"></i>");
			} else if (board.code.equals("notice")) {
				boardList.append("<i class=\"fas fa-flag\"></i>");
			}
			boardList.append("<span>" + board.code.toUpperCase() + "</span>");
			boardList.append("</a>");
			boardList.append("</li>");

			if (keyword.equals("home")) {
				articleListInBoard(board);
			}
		}

		head = head.replace("${menu-bar__menu-1__board-menu-content}", boardList);
		return head;
	}

	// 별도의 쓰레드를 켜서 build site 명령을 10초에 한번씩 수행한다. 즉 자동빌드켜기
	public void StartAutoSite() {
		buildSiteAuto = true;
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
