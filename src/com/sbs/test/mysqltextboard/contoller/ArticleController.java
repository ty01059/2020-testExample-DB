package com.sbs.test.mysqltextboard.contoller;

import java.util.*;

import com.sbs.test.mysqltextboard.container.Container;
import com.sbs.test.mysqltextboard.dto.Article;
import com.sbs.test.mysqltextboard.service.ArticleService;
import com.sbs.test.mysqltextboard.service.MemberService;
import com.sbs.test.mysqltextboard.session.Session;

public class ArticleController extends Controller {

	private ArticleService articleService;
	private MemberService memberService;
	private Scanner sc;
	private Session session;
	private Map<String, Object> board;

	public ArticleController() {
		articleService = Container.aritlceSerivce;
		memberService = Container.memberSerivce;
		sc = Container.scanner;
		session = Container.session;

		board = new HashMap<>();
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
		} else if (code.equals("createBoard")) {
			articleCreateBoard();
		} else if (code.equals("selectBoard")) {
			articleSelectBoard(cmd);
		}
	}

	private void articleList() {
		String boardName = session.getSelectBoardId() == 0 ? "전체" : (String) board.get("name");
		System.out.printf("== %s 게시물 리스트 ==\n", boardName);

		List<Article> articles = articleService.getArticles();

		if (articles == null) {
			System.out.println("작성된 게시물이 없습니다.");
			return;
		}

		System.out.println("id  /  작성자  /  내용  /  제목  /   작성시간  /  수정시간  /  boardId");
		for (Article article : articles) {
			Map<String, Object> member = memberService.getMember(article.memberId);

			if (boardName.equals("전체")) {
				System.out.printf("%d  /  %s  /  %s  /  %s  /  %s  /  %s  /  %d\n", article.id, member.get("memberId"),
						article.title, article.body, article.regDate, article.updatedate, article.boardId);
			} else if ((int) board.get("id") == article.boardId) {
				System.out.printf("%d  /  %s  /  %s  /  %s  /  %s  /  %s  /  %d\n", article.id, member.get("memberId"),
						article.title, article.body, article.regDate, article.updatedate, article.boardId);
			}
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
		int memberId = (int) session.getLoginUser().get("id");
		int boardId = session.getSelectBoardId();

		int id = articleService.add(title, body, memberId, boardId);

		System.out.printf("%d번 게시물이 등록되었습니다.\n", id);
	}

	private void articleUpdate(String cmd) {
		System.out.println("== 게시물 업데이트 ==");

		int index = Integer.parseInt(cmd.split(" ")[2]);
		int result = articleService.update(index);

		if (result == 0) {
			System.out.println("작성된 게시물이 없습니다.");
			return;
		}
		System.out.printf("%d번 게시물이 업데이트되었습니다.\n", index);
	}

	private void articleModify(String cmd) {
		System.out.println("== 게시물 수정 ==");

		if (!session.getLogined()) {
			System.out.println("로그인이 필요합니다.");
			return;
		}

		if (cmd.split(" ").length != 3) {
			System.out.println("다시 입력해라");
			return;
		}

		int index = Integer.parseInt(cmd.split(" ")[2]);

		System.out.printf("title : ");
		String title = sc.nextLine();
		System.out.printf("body : ");
		String body = sc.nextLine();
		int id = (int) session.getLoginUser().get("id");

		Map<String, Object> article = articleService.getArticle(index);

		if (article == null) {
			System.out.println("없는 게시물입니다.");
			return;
		} else if ((int) article.get("memberId") != id) {
			System.out.println("로그인된 사용자에게 권한이 없습니다.");
			return;
		}
		articleService.modify(index, title, body, id);
		System.out.printf("%d번 게시물이 수정되었습니다.\n", index);
	}

	private void articleDetail(String cmd) {
		System.out.println("== 게시물 상세 ==");
		int index = Integer.parseInt(cmd.split(" ")[2]);
		Map<String, Object> article = articleService.getArticle(index);

		if (article == null) {
			System.out.println("존재하지 않는 게시물입니다.");
			return;
		}

		System.out.printf("id : %d\n", (int) article.get("id"));
		System.out.printf("작성시간 : %s\n", article.get("regDate"));
		System.out.printf("수정시간 : %s\n", article.get("updatedate"));
		Map<String, Object> member = memberService.getMember((int) article.get("memberId"));
		System.out.printf("작성자 : %s\n", member.get("memberId"));
		System.out.printf("제목 : %s\n", article.get("title"));
		System.out.printf("내용 : %s\n", article.get("body"));
	}

	private void articleDelete(String cmd) {
		System.out.println("== 게시물 삭제 ==");

		int index = Integer.parseInt(cmd.split(" ")[2]);

		Map<String, Object> article = articleService.getArticle(index);
		int id = (int) session.getLoginUser().get("id");

		if (article == null) {
			System.out.println("없는 게시물입니다.");
			return;
		} else if ((int) article.get("memberId") != id) {
			System.out.println("로그인된 사용자에게 권한이 없습니다.");
			return;
		}

		articleService.delete(index);
		System.out.printf("%d번 게시물이 삭제되었습니다.\n", index);
	}

	private void articleCreateBoard() {
		System.out.println("== 게시판 추가 ==");

		System.out.printf("boardName : ");
		String boardName = sc.nextLine();

		int id = articleService.createBoard(boardName);
		if (id == -1) {
			System.out.printf("%s 게시판이름이 이미 생성되어있습니다.\n", boardName);
		}
		System.out.printf("%s(%d번) 게시판이 생성되었습니다.\n", boardName, id);
	}

	private void articleSelectBoard(String cmd) {
		String id = cmd.split(" ")[2];
		System.out.println("== 게시판 선택 ==");

		if (id.equals("0")) {
			session.setSelectBoardId(0);
			return;
		}
		board = articleService.selectBoard(id);
		session.setSelectBoardId((int) board.get("id"));

		System.out.printf("%s(%d번) 게시판이 선택되었습니다.\n", board.get("name"), board.get("id"));
	}
}
