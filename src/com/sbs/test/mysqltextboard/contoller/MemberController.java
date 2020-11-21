package com.sbs.test.mysqltextboard.contoller;

import java.util.Scanner;

import com.sbs.test.mysqltextboard.container.Container;
import com.sbs.test.mysqltextboard.dto.Member;
import com.sbs.test.mysqltextboard.service.MemberService;
import com.sbs.test.mysqltextboard.session.Session;

public class MemberController extends Controller {

	private Scanner sc;
	private Session session;
	private MemberService memberService;

	public MemberController() {
		sc = Container.scanner;
		session = Container.session;
		memberService = Container.memberService;
	}

	public void doCmd(String cmd) {
		String keyword = cmd.split(" ")[1];

		if (keyword.equals("join")) {
			doJoin();
		} else if (keyword.equals("login")) {
			doLogin();
		} else if (keyword.equals("whoami")) {
			doWhoami();
		} else if (keyword.equals("logout")) {
			doLogout();
		} else {
			return;
		}
	}

	private void doJoin() {
		System.out.println("== 회원가입 ==");

		System.out.printf("id : ");
		String id = sc.nextLine();

		if (memberService.getMemberId(id) != null) {
			System.out.println("이미 가입된 id가 존재합니다.");
			return;
		}

		System.out.printf("pw : ");
		String pw = sc.nextLine();
		System.out.printf("name : ");
		String name = sc.nextLine();

		memberService.join(id, pw, name);

		System.out.printf("%s님 환영합니다.\n", id);
	}

	private void doLogin() {
		System.out.println("== 로그인 ==");

		System.out.printf("id : ");
		String id = sc.nextLine();
		System.out.printf("pw : ");
		String pw = sc.nextLine();

		Member member = memberService.login(id, pw);

		if (member == null) {
			System.out.println("id 또는 pw가 일치하지 않습니다.");
			return;
		}

		session.setLogined(true);
		session.setLoginMember(member);
		System.out.println("로그인 되었습니다.");
	}

	private void doWhoami() {
		System.out.println("== 로그인 사용자 정보 ==");

		Member member = session.getLoginMember();
		System.out.printf("id : %d\n", member.id);
		System.out.printf("memberId : %s\n", member.memberId);
		System.out.printf("pw : %s\n", member.password);
		System.out.printf("name : %s\n", member.name);
		System.out.printf("계정권한 : %s\n", member.getType());
	}

	private void doLogout() {
		System.out.println("== 로그아웃 ==");

		session.setLogined(false);
		session.setLoginMember(null);

		System.out.println("로그아웃 되었습니다.");
	}
}
