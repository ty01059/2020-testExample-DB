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
		memberService = Container.memberSerivce;
	}

	public void doCmd(String cmd) {
		if (cmd.equals("member join")) {
			memberJoin();
		} else if (cmd.equals("member login")) {
			memberLogin();
		} else if (cmd.equals("member whoami")) {
			memeberInfo();
		} else if (cmd.equals("member logout")) {
			memberLogout();
		}
	}

	private void memberJoin() {
		System.out.println("== 회원가입 ==");

		System.out.printf("ID : ");
		String id = sc.nextLine();
		System.out.printf("Password : ");
		String pw = sc.nextLine();
		System.out.printf("Name : ");
		String name = sc.nextLine();

		int result = memberService.join(id, pw, name);
		if (result == -1) {
			System.out.println("이미 생성된 아이디입니다.");
			return;
		}
		System.out.printf("%s님 환영합니다.\n", id);
	}

	private void memberLogin() {
		System.out.println("== 로그인 ==");

		if (session.getLogined()) {
			System.out.println("이미 로그인이 되어있습니다.");
			return;
		}

		System.out.printf("ID : ");
		String id = sc.nextLine();
		System.out.printf("Password : ");
		String pw = sc.nextLine();

		Member member = memberService.login(id, pw);
		if (member == null) {
			System.out.println("사용자 정보가 없는 정보입니다.");
			return;
		}
		System.out.printf("%s님 로그인되었습니다.\n", id);
		session.setLogined(true);
		session.setLoginUser(member);
	}

	private void memeberInfo() {
		System.out.println("== 로그인 사용자 정보 ==");

		if (!session.getLogined()) {
			System.out.println("로그인이 필요합니다.");
			return;
		}

		Member member = session.getLoginUser();

		System.out.printf("id : %d\n", member.id);
		System.out.printf("memberId : %s\n", member.memberId);
		System.out.printf("pw : %s\n", member.password);
		System.out.printf("name : %s\n", member.name);
	}

	private void memberLogout() {
		System.out.println("== 로그아웃 ==");

		if (!session.getLogined()) {
			System.out.println("로그인이 필요합니다.");
			return;
		}

		memberService.logout();
	}
}
