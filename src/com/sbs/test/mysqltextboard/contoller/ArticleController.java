package com.sbs.test.mysqltextboard.contoller;

import java.util.*;

import com.sbs.test.mysqltextboard.container.Container;
import com.sbs.test.mysqltextboard.dto.Article;
import com.sbs.test.mysqltextboard.dto.Board;
import com.sbs.test.mysqltextboard.dto.Member;
import com.sbs.test.mysqltextboard.dto.Reply;
import com.sbs.test.mysqltextboard.service.ArticleService;
import com.sbs.test.mysqltextboard.service.MemberService;
import com.sbs.test.mysqltextboard.session.Session;

public class ArticleController extends Controller {

	private ArticleService articleService;
	private MemberService memberService;
	private Scanner sc;
	private Session session;
	private Board board;

	public ArticleController() {
		articleService = Container.aritlceSerivce;
		memberService = Container.memberSerivce;
		sc = Container.scanner;
		session = Container.session;

		board = new Board();
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
		} else if (code.equals("writeReply")) {
			articleReply(cmd);
		} else if (code.equals("modifyReply")) {

		} else if (code.equals("deleteReply")) {

		}
	}

	private void articleList() {
		String boardName = session.getSelectBoardId() == 0 ? "전체" : board.name;
		System.out.printf("== %s 게시물 리스트 ==\n", boardName);

		System.out.println("id  /  작성자  /  내용  /  제목  /   작성시간  /  수정시간  /  boardId");

		if (session.getSelectBoardId() == 0) {
			List<Article> articles = articleService.getArticles();

			if (articles == null) {
				System.out.println("작성된 게시물이 없습니다.");
				return;
			}
			for (Article article : articles) {
				Member member = memberService.getMember(article.memberId);
				System.out.printf("%d  /  %s  /  %s  /  %s  /  %s  /  %s  /  %d\n", article.id, member.memberId,
						article.title, article.body, article.regDate, article.updatedate, article.boardId);
			}
		} else {
			List<Article> articles = articleService.getArticles(board.id);

			if (articles == null) {
				System.out.println("작성된 게시물이 없습니다.");
				return;
			}
			for (Article article : articles) {
				Member member = memberService.getMember(article.memberId);
				System.out.printf("%d  /  %s  /  %s  /  %s  /  %s  /  %s  /  %d\n", article.id, member.memberId,
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
		int memberId = session.getLoginUser().id;
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
		int id = session.getLoginUser().id;

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
		System.out.println("== 게시물 상세 ==");
		int index = Integer.parseInt(cmd.split(" ")[2]);
		Article article = articleService.getArticle(index);

		if (article == null) {
			System.out.println("존재하지 않는 게시물입니다.");
			return;
		}
		Member member = new Member();
		System.out.printf("id : %d\n", article.id);
		System.out.printf("작성시간 : %s\n", article.regDate);
		System.out.printf("수정시간 : %s\n", article.updatedate);
		member = memberService.getMember(article.memberId);
		System.out.printf("작성자 : %s\n", member.memberId);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);

		System.out.println("======== 댓글 ========");
		List<Reply> reply = articleService.getReply((int) article.id);
		System.out.println("id  /  작성자  /  작성시간  /  내용");
		for (Reply r : reply) {
			member = memberService.getMember(r.memberId);
			System.out.printf("%d  /  %s  /  %s  /  %s\n", r.id, member.memberId, r.regDate, r.body);
		}
	}

	private void articleDelete(String cmd) {
		System.out.println("== 게시물 삭제 ==");

		int index = Integer.parseInt(cmd.split(" ")[2]);

		Article article = articleService.getArticle(index);
		int id = session.getLoginUser().id;

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
		int id = Integer.parseInt(cmd.split(" ")[2]);
		System.out.println("== 게시판 선택 ==");

		if (id == 0) {
			session.setSelectBoardId(0);
			return;
		}
		board = articleService.selectBoard(id);
		session.setSelectBoardId(board.id);

		System.out.printf("%s(%d번) 게시판이 선택되었습니다.\n", board.name, board.id);
	}

	private void articleReply(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]);

		if (!session.getLogined()) {
			System.out.println("로그인이 필요합니다.");
			return;
		}

		System.out.println("== 댓글 작성 ==");

		System.out.printf("내용 : ");
		String body = sc.nextLine();

		int result = articleService.writeReply(body, id, session.getLoginUser().id);

		System.out.printf("%d번글에 %d번 댓글이 추가되었습니다.\n", id, result);
	}
}
