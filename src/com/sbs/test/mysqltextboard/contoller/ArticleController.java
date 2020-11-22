package com.sbs.test.mysqltextboard.contoller;

import java.util.*;

import com.sbs.test.mysqltextboard.container.Container;
import com.sbs.test.mysqltextboard.dto.Article;
import com.sbs.test.mysqltextboard.dto.Board;
import com.sbs.test.mysqltextboard.dto.Member;
import com.sbs.test.mysqltextboard.service.ArticleService;
import com.sbs.test.mysqltextboard.service.MemberService;
import com.sbs.test.mysqltextboard.session.Session;

public class ArticleController extends Controller {

	private ArticleService articleService;
	private MemberService memberService;
	private Scanner sc;
	private Session session;
	private Board board;

	private int two = 2;
	private int thr = 3;

	public ArticleController() {
		articleService = Container.aritlceService;
		memberService = Container.memberService;
		sc = Container.scanner;
		session = Container.session;
		session.setBoardId(1);
		board = new Board();
	}

	public void doCmd(String cmd) {
		String code = cmd.split(" ")[1];

		if (code.equals("createBoard")) {
			errMsg(cmd, two);
			articleCreateBoard();
		} else if (code.equals("selectBoard")) {
			errMsg(cmd, thr);
			articleSelectBoard();
		} else if (code.equals("list")) {
			errMsg(cmd, two);
			articleList();
		} else if (code.equals("write")) {
			errMsg(cmd, two);
			articleAdd();
		} else if (code.equals("update")) {
			errMsg(cmd, thr);
			articleUpdate(cmd);
		} else if (code.equals("modify")) {
			errMsg(cmd, thr);
			articleModify(cmd);
		} else if (code.equals("detail")) {
			errMsg(cmd, thr);
			articleDetail(cmd);
		} else if (code.equals("delete")) {
			errMsg(cmd, thr);
			articleDelete(cmd);
		} else if (code.equals("recommand")) {
			errMsg(cmd, thr);
			articleRecommand(cmd);
		} else if (code.equals("cancelRecommand")) {
			errMsg(cmd, thr);
			articleCancelRecommand(cmd);
		} else if (code.equals("writeReply")) {
			errMsg(cmd, thr);
			articleReply(cmd);
		} else if (code.equals("modifyReply")) {
			errMsg(cmd, thr);
			articleModifyReply(cmd);
		} else if (code.equals("deleteReply")) {
			errMsg(cmd, thr);
			articleDeleteReply(cmd);
		}
	}

	private void errMsg(String cmd, int i) {
		if (cmd.split(" ").length != i) {
			System.out.println("잘못입력하셨습니다.");
			return;
		}
	}

	// ######################## 게시판 #################################

	private void articleCreateBoard() {
		System.out.println("== 게시판 작성 ==");

		Member member = memberService.getMemberId(session.getLoginMember().memberId);
		if (!member.isAdmin()) {
			System.out.println("관리자만 게시판을 생성할 수 있습니다.");
			return;
		}

		System.out.printf("boardName : ");
		String boardName = sc.nextLine();

		if (articleService.getBoardWithName(boardName)) {
			System.out.println("게시판 이름이 이미 존재합니다.");
			return;
		}

		System.out.printf("code : ");
		String code = sc.nextLine();

		if (articleService.getBoardWithCode(code)) {
			System.out.println("게시판 코드가 이미 존재합니다.");
			return;
		}

		int id = articleService.makeBoard(boardName, code);
		System.out.printf("%s(%d번) 게시판이 생성되었습니다.\n", boardName, id);
	}

	private void articleSelectBoard() {
		System.out.println("== 게시판 선택 ==");

		System.out.println("= 게시판 목록 =");
		List<Board> boards = articleService.getBoards();

		System.out.println("번호  /  이름  /  코드  /  게시물 수");
		for (Board board : boards) {
			System.out.printf("%d  /  %s  /  %s  /  %d\n", board.id, board.name, board.code, board.articleCount);
		}

		System.out.printf("code : ");
		String code = sc.nextLine();

		if (!articleService.getBoardWithCode(code)) {
			System.out.println("존재하지않는 게시판입니다.");
			return;
		}

		board = articleService.getBoard(code);
		session.setBoardId(board.id);

		System.out.printf("%s(%d번) 게시판이 선택되었습니다.\n", board.name, board.id);
	}

	// ######################## 게시물 #################################

	private void articleList() {
		Board board = articleService.getBoard(session.getBoardId());
		System.out.printf("== %s 게시물 리스트 ==\n", board.name);

		List<Article> articles = new ArrayList<>();
		articles = articleService.getArticles(session.getBoardId());

		if (articles == null) {
			System.out.println("작성된 게시물이 없습니다.");
			return;
		}

		System.out.println("id  /  작성시간  /  수정시간  /  작성자  /  내용  /  제목  /  조회수");

		for (Article article : articles) {
			System.out.printf("%d  /  %s  /  %s  /  %s  /  %s  /  %s  /  %d\n", article.id, article.regDate,
					article.updateDate, article.writer, article.title, article.body, article.view);
		}
	}

	private void articleAdd() {
		System.out.println("== 게시물 생성 ==");
		if (!session.getLogined()) {
			System.out.println("로그인이 필요합니다.");
			return;
		}

		System.out.printf("title : ");
		String title = sc.nextLine();
		System.out.printf("body : ");
		String body = sc.nextLine();
		int memberId = session.getLoginMember().id;
		int boardId = session.getBoardId();

		int id = articleService.add(title, body, memberId, boardId);
		System.out.printf("%d번 게시물이 생성되었습니다.\n", id);
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
		int id = session.getLoginMember().id;

		Article article = articleService.getArticle(index);

		if (article == null) {
			System.out.println("없는 게시물입니다.");
			return;
		} else if (article.memberId != id) {
			System.out.println("로그인된 사용자에게 권한이 없습니다.");
			return;
		}
		articleService.modify(index, title, body, id);
		System.out.printf("%d번 게시물이 수정되었습니다.\n", index);
	}

	private void articleDetail(String cmd) {
		if (!session.getLogined()) {
			System.out.println("로그인이 필요합니다.");
			return;
		}

		System.out.println("== 게시물 상세 ==");

		int id = Integer.parseInt(cmd.split(" ")[2]);

		Article article = articleService.getArticle(id);
		if (article == null) {
			System.out.println("게시물이 존재하지 않습니다.");
			return;
		}
		article.view += 1;
		articleService.setArticleCount(article.id, article.view);

		System.out.printf("id : %d\n", article.id);
		System.out.printf("regDate : %s\n", article.regDate);
		System.out.printf("updateDate : %s\n", article.updateDate);
		System.out.printf("title : %s\n", article.title);
		System.out.printf("body : %s\n", article.body);
		System.out.printf("writer : %s\n", article.writer);
		System.out.printf("view : %s\n", article.view);
	}

	private void articleDelete(String cmd) {
		System.out.println("== 게시물 삭제 ==");

		int index = Integer.parseInt(cmd.split(" ")[2]);

		Article article = articleService.getArticle(index);
		int id = session.getLoginMember().id;

		if (article == null) {
			System.out.println("없는 게시물입니다.");
			return;
		} else if ((int) article.memberId != id) {
			System.out.println("로그인된 사용자에게 권한이 없습니다.");
			return;
		}
		articleService.delete(index);

		System.out.printf("%d번 게시물이 삭제되었습니다.\n", index);
	}

	private void articleRecommand(String cmd) {

		System.out.println("== 게시물 추천 ==");

		int articleId = Integer.parseInt(cmd.split(" ")[2]);
		int memberId = session.getLoginMember().id;

		if (!session.getLogined()) {
			System.out.println("로그인이 필요합니다.");
			return;
		}
		
		articleService.Recommand(articleId, memberId);

	}

	private void articleCancelRecommand(String cmd) {

		System.out.println("== 게시물 추천 취소 ==");

		int articleId = Integer.parseInt(cmd.split(" ")[2]);

		if (!session.getLogined()) {
			System.out.println("로그인이 필요합니다.");
			return;
		}
	}

	// ######################## 댓글 #################################

	private void articleReply(String cmd) {
		if (cmd.split(" ").length != 3) {
			return;
		}
		int id = Integer.parseInt(cmd.split(" ")[2]);

		System.out.println("== 댓글 작성 ==");

		if (!session.getLogined()) {
			System.out.println("로그인이 필요합니다.");
			return;
		}

		System.out.printf("내용 : ");
		String body = sc.nextLine();

		int result = articleService.writeReply(body, id, session.getLoginMember().id);

		System.out.printf("%d번글에 %d번 댓글이 추가되었습니다.\n", id, result);
	}

	private void articleModifyReply(String cmd) {
		if (cmd.split(" ").length != 3) {
			return;
		}

		int id = Integer.parseInt(cmd.split(" ")[2]);

		if (!session.getLogined()) {
			System.out.println("로그인이 필요합니다.");
			return;
		}

		System.out.println("== 댓글 수정 ==");

		System.out.printf("내용 : ");
		String body = sc.nextLine();

		int index = articleService.modifyReply(id, body, session.getLoginMember().id);
		if (index == -1) {
			System.out.println("댓글이 없거나 수정 권한이 없습니다.");
			return;
		}
		System.out.println("댓글이 수정되었습니다.");
	}

	private void articleDeleteReply(String cmd) {
		if (cmd.split(" ").length != 3) {
			return;
		}

		int id = Integer.parseInt(cmd.split(" ")[2]);

		if (!session.getLogined()) {
			System.out.println("로그인이 필요합니다.");
			return;
		}

		int result = articleService.deleteReply(id, session.getLoginMember().id);
		if (result == 0) {
			System.out.println("댓글이 없거나 삭제 권한이 없습니다.");
			return;
		}
		System.out.println("댓글이 삭제되었습니다.");
	}
}
