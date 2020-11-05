package com.sbs.test.textboard.controller;

import java.util.Scanner;

import com.sbs.test.textboard.container.Container;
import com.sbs.test.textboard.dto.Member;
import com.sbs.test.textboard.service.MemberService;

public class MemberController extends Controller {
	private Scanner sc;
	private MemberService memberService;

	private int count;
	private int maxCount;

	public MemberController() {
		sc = Container.scanner;
		memberService = new MemberService();
		count = 1;
		maxCount = 3;
	}

	public void doCommand(String command) {

		if (command.equals("member join")) {
			join();
		} else if (command.equals("member login")) {
			login();
		} else if (command.equals("member whoami")) {
			info();
		} else if (command.equals("member logout")) {
			logout();
		} else {
			System.out.println("잘못입력하셨습니다.");
		}
	}

	private void join() {
		System.out.println("== 회원가입 ==");

		if (Container.session.getLogined()) {
			System.out.println("로그아웃 후 이용해주십시오.");
			return;
		}

		while (true) {
			if (count > maxCount) {
				System.out.println("잠시후 다시 시도해주십시오.");
				break;
			}

			System.out.printf("ID : ");
			String memberId = sc.nextLine().trim();

			boolean con = inputNullCheck(memberId, "ID");
			if (con) {
				continue;
			}

			System.out.printf("PW : ");
			String pw = sc.nextLine().trim();

			con = inputNullCheck(pw, "PW");
			if (con) {
				continue;
			}

			System.out.printf("NAME : ");
			String name = sc.nextLine().trim();

			con = inputNullCheck(name, "NAME");
			if (con) {
				continue;
			}

			int result = memberService.memberJoin(memberId, pw, name);
			if (result == -1) {
				System.out.println("입력하신 ID는 이미 회원가입 되어있습니다.");
				count++;
				continue;
			}

			System.out.printf("%s님 회원가입이 완료되었습니다.\n", memberId);
			break;
		}
	}

	private void login() {
		System.out.println("== 로그인 ==");

		while (true) {
			if (count > maxCount) {
				System.out.println("잠시후 다시 시도해주십시오.");
				break;
			}

			System.out.printf("ID : ");
			String memberId = sc.nextLine().trim();

			boolean con = inputNullCheck(memberId, "ID");
			if (con) {
				continue;
			}

			System.out.printf("PW : ");
			String pw = sc.nextLine().trim();

			con = inputNullCheck(pw, "PW");
			if (con) {
				continue;
			}

			int result = memberService.memberLogin(memberId, pw);
			if (result == -1) {
				System.out.println("잘못입력하셨거나 없는 정보입니다.");
				continue;
			}

			System.out.println("로그인 되었습니다.");
			break;
		}
	}

	private void info() {
		System.out.println("== 로그인된 사용자 정보 ==");

		Member member = memberService.memberWhoami();
		if (member == null) {
			System.out.println("로그인해주시기 바랍니다.");
			return;
		}

		System.out.printf("id : %d\n", member.id);
		System.out.printf("memberId : %s\n", member.memberId);
		System.out.printf("pw : %s\n", member.pw);
		System.out.printf("name : %s\n", member.name);
	}

	private void logout() {
		System.out.println("== 로그아웃 ==");

		int result = memberService.memberLogout();

		if (result == -1) {
			System.out.println("로그인이 필요합니다.");
			return;
		}
		System.out.println("로그아웃 되었습니다.");
	}

	private boolean inputNullCheck(String input, String tag) {
		if (input.equals("")) {
			System.out.printf("%s(을)를 입력해주세요.\n", tag);
			count++;
			return true;
		}
		return false;
	}
}
