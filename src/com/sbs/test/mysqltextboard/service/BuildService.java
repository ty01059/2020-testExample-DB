package com.sbs.test.mysqltextboard.service;

import java.util.List;

import com.sbs.test.mysqltextboard.container.Container;
import com.sbs.test.mysqltextboard.dto.Article;
import com.sbs.test.mysqltextboard.dto.Board;
import com.sbs.test.mysqltextboard.fileUtil.Util;

public class BuildService {

	private ArticleService articleService;
//	private MemberService memberService;
	private boolean buildSiteAuto;

	private String foot = Util.getFileContents("site_template/foot.html");

	public BuildService() {
		articleService = Container.articleService;
//		memberService = Container.memberService;
	}

	public void buildSite() {

		Util.rmdir("site");
		Util.mkdirs("site/home");
		Util.copy("site_template/app.css", "site/app.css");

		StringBuilder sb = new StringBuilder();
		String head = getHeadHtml("home");

		sb.append(head);

		sb.append("<main class=\"flex-grow-1\">");
		sb.append("<section class=\"title-bar con-min-width\">");
		sb.append("<h1 class=\"con\">");
		sb.append("<i class=\"fas fa-home\"></i> <span>HOME</span>");
		sb.append("</h1>");
		sb.append("</section>");
		sb.append("<section class=\"section-1 con-min-width\">");
		sb.append(
				"<div class=\"con\">Lorem ipsum dolor sit amet consectetur adipisicing elit. Ipsam, esse ullam nulla id unde ex "
						+ "dicta sequi, repellat temporibus quis amet ipsa provident magni veniam explicabo, repudiandae fuga in. In.</div>");
		sb.append("</section>");
		sb.append("</main>");

		sb.append(foot);

		String filePath = "site/home/index.html";

		Util.writeFile(filePath, sb.toString());
	}

	private String getHeadHtml(String keyword) {
		String head = Util.getFileContents("site_template/head.html");
		List<Board> boards = articleService.getBoards();
		StringBuilder boardList = new StringBuilder();

		for (Board board : boards) {
			boardList.append("<li>");
			boardList.append("<a href=\"../article/" + board.code + "-list_1.html\" class=\"block\">");
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

	private void articleListInBoard(Board board) {
		System.out.println("site/article 폴더 생성");
		Util.mkdirs("site/article");

		List<Article> articles = articleService.getArticles(board.id);
		String head = getHeadHtml("board");
		int itemsInAPage = 10;
		int articleCount = articles.size();
		int totalPage = (int) Math.ceil((double) articleCount / itemsInAPage);

		for (int i = 1; i <= totalPage; i++) {
			StringBuilder sb = new StringBuilder();

			sb.append(head);
			sb.append("<main class=\"flex-grow-1\">");
			sb.append("<div class=\"flex flex-column\">");

			sb.append("<section class=\"title-bar con-min-width\">");
			sb.append("<h1 class=\"con\">");
			sb.append("<div class=\"title-bar con-min-width\">");
			sb.append("<h1 class=\"con\">");
			sb.append("<i class=\"fas fa-flag\"></i>");
			sb.append("<span>" + board.code.toUpperCase() + " LIST</span>");
			sb.append("</h1>");
			sb.append("</div>");
			sb.append("</h1>");
			sb.append("</section>");

			sb.append("<section class=\"section-1 con-min-width\">");
			sb.append("<div class=\"con\">");
			sb.append("<div class=\"article-list\">");
			sb.append("<header>");
			sb.append("<div class=\"flex\">");
			sb.append("<div class=\"article-list__cell-id\">번호</div>");
			sb.append("<div class=\"article-list__cell-reg-date\">날짜</div>");
			sb.append("<div class=\"article-list__cell-writer\">작성자</div>");
			sb.append("<div class=\"article-list__cell-title\">제목</div>");
			sb.append("</div>");
			sb.append("</header>");

			sb.append("<main>");

			int startList = (i - 1) * itemsInAPage;
			int end = startList + itemsInAPage - 1;
			if (end >= articleCount) {
				end = startList - 1;
			}

			for (int j = startList; j <= end; j++) {
				if (j > articles.size() - 1) {
					break;
				}
				sb.append("<div class=\"flex\">");
				sb.append("<div class=\"article-list__cell-id\">" + articles.get(j).id + "</div>");
				sb.append("<div class=\"article-list__cell-reg-date\">" + articles.get(j).regDate + "</div>");
				sb.append("<div class=\"article-list__cell-writer\">" + articles.get(j).writer + "</div>");
				sb.append("<div class=\"article-list__cell-title\">");
				sb.append("<a href=\"../article/" + articles.get(j).id + ".html\" class=\"hover-underline\">"
						+ articles.get(j).title + "</a>");
				sb.append("</div>");
				sb.append("</div>");
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

			sb.append("<div class=\"article-page-menu\">");
			sb.append("<ul class=\"flex flex-jc-c\">");
			if (pageBoxStartBeforeBtnNeedToShow) {
				sb.append("<li><a href=\"../article/" + board.code + "-list_" + pageBoxStartBeforePage + ".html\" class=\"flex flex-ai-c\"> &lt; 이전</a></li>");
			}
			for (int j = pageBoxStartPage; j <= pageBoxEndPage; j++) {
				String selectedClass = "";
				if (j == i) {
					selectedClass = "article-page-menu__link--selected";
				}

				sb.append("<li><a href=\"../article/" + board.code + "-list_" + j + ".html\" class=\"flex flex-ai-c "
						+ selectedClass + "\">" + j + " </a></li>");
			}
			if (pageBoxEndAfterBtnNeedToShow) {
				sb.append("<li><a href=\"../article/" + board.code + "-list_" + pageBoxEndAfterPage + ".html\" class=\"flex flex-ai-c\">다음 &gt;</a></li>");
			}
			sb.append("</ul>");
			sb.append("</div>");

			sb.append("</div>");
			sb.append("</section>");
			sb.append("</div>");
			sb.append("</main>");

			sb.append(foot);

			String filePath = "site/article/" + board.code + "-list_" + i + ".html";

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
		String head = getHeadHtml("detail");

		sb.append(head);

		sb.append("<main class=\"flex-grow-1\">");
		sb.append("<div class=\"flex flex-column\">");

		sb.append("<section class=\"title-bar con-min-width\">");
		sb.append("<h1 class=\"con\">");
		sb.append("<div class=\"title-bar con-min-width\">");
		sb.append("<h1 class=\"con\">");
		sb.append("<i class=\"fas fa-clipboard-list\"></i>");
		sb.append("<span>게시물 상세페이지</span>");
		sb.append("</h1>");
		sb.append("</div>");
		sb.append("</h1>");
		sb.append("</section>");

		sb.append("<div class=\"con con-min-width\">");

		sb.append("<section class=\"article_detail\">");

		sb.append("<div class=\"title\">");
		sb.append("<div>[" + board.name + "]</div>");
		sb.append("<div>" + article.title + "</div>");
		sb.append("</div>");
		sb.append("<div class=\"writer\">");
		sb.append("<div><i class=\"fas fa-user-edit\"></i>&nbsp;" + article.writer + "</div>");
		sb.append("<div>" + article.regDate + "</div>");
		sb.append("<div><i class=\"fas fa-eye\"></i>&nbsp;" + article.view + "</div>");
		sb.append("</div>");
		sb.append("<div class=\"body\">" + article.body + "</div>");
		sb.append("</section>");

		sb.append("<section class=\"button\">");
		sb.append("<div class=\"flex\">");

		String previousdisable = "";
		String nextdisable = "";
		if (previousArticleId == 0) {
			previousdisable = " class=\"a-pointer-events-none\"";
		}
		if (nextArticleId == 0) {
			nextdisable = " class=\"a-pointer-events-none\"";
		}

		sb.append("<a href=\"../article/" + previousArticleId + ".html\"" + previousdisable + ">이전글</a><br>");
		sb.append("<a href=\"../article/" + board.code + "-list_" + page + ".html\">목록</a><br>");
		sb.append("<a href=\"../article/" + nextArticleId + ".html\"" + nextdisable + ">다음글</a><br>");
		sb.append("</div>");
		sb.append("</section>");

		sb.append("</div>");
		sb.append("</main>");

		sb.append(foot);

		String fileName = article.id + ".html";

		String filePath = "site/article/" + fileName;

		Util.writeFile(filePath, sb.toString());

		System.out.println(filePath + " 생성");
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
