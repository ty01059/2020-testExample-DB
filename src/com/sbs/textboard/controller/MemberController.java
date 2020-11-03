package com.sbs.textboard.controller;

import java.util.Scanner;

import com.sbs.textboard.container.Container;
import com.sbs.textboard.service.MemberService;
import com.sbs.textboard.session.Session;

public class MemberController {

	private MemberService memberService;
	private Session session;
	private int maxCount;

	public MemberController() {
		memberService = new MemberService();
		session = Container.session;
		maxCount = 3;
	}

	public void run(Scanner sc, String command) {
		if (command.equals("member login")) {
			System.out.println("== 로그인 ==");

			if (session.getIsLogined()) {
				System.out.println("이미 로그인되어 있습니다.");
				return;
			}

			int loginCount = 1;
			while (true) {
				if (loginCount > maxCount) {
					System.out.println("잠시후 다시 시도해주십시오.");
					break;
				}

				System.out.printf("ID : ");
				String id = sc.nextLine().trim();
				System.out.printf("PW : ");
				String pw = sc.nextLine().trim();

				int result = memberService.memberLogin(id, pw);
				if (result == 1) {
					System.out.println("로그인이 완료되었습니다.");
					return;
				} else {
					System.out.println("잘못입력하셨거나 없는 정보입니다.");
					loginCount++;
				}
			}
		} else {
			System.out.println("잘못입력하셨습니다.");
			return;
		}
	}
}