package com.sbs.textboard.controller;

import java.util.Scanner;

import com.sbs.textboard.container.Container;
import com.sbs.textboard.dto.MemberDTO;
import com.sbs.textboard.service.MemberService;
import com.sbs.textboard.session.Session;

public class MemberController extends Controller {

	private MemberService memberService;
	private Session session;
	private Scanner sc;
	private int maxCount;

	public MemberController() {
		memberService = new MemberService();
		session = Container.session;
		sc = Container.scanner;
		maxCount = 3;
	}

	public void doCommand(String command) {

		if (command.equals("member join")) {
			System.out.println("== 회원가입 ==");

			if (session.getIsLogined()) {
				System.out.println("이미 로그인되어 있습니다.");
				return;
			}

			int joinCount = 1;

			while (true) {

				if (joinCount > maxCount) {
					System.out.println("잠시후 다시 시도해주시기 바랍니다.");
					break;
				}

				System.out.printf("ID : ");
				String id = sc.nextLine().trim();
				System.out.printf("PW : ");
				String pw = sc.nextLine().trim();
				System.out.printf("NAME : ");
				String name = sc.nextLine().trim();

				if (id.equals("") || pw.equals("") || name.equals("")) {
					System.out.println("빈칸을 채워주시기 바랍니다.");
					joinCount++;
					continue;
				}

				int result = memberService.memberJoin(id, pw, name);

				if (result == -1) {
					System.out.println("이미 가입된 ID 정보가 있습니다.");
				} else if (result == -2) {
					System.out.println("이미 가입된 NAME 정보가 있습니다.");
				} else {
					System.out.printf("%s님 회원가입이 완료되었습니다.\n", id);
					break;
				}
				joinCount++;
			}

		} else if (command.equals("member login")) {
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
		} else if (command.equals("member logout")) {
			System.out.println("== 로그아웃 ==");

			int result = memberService.memberLogout();

			if (result == -1) {
				System.out.println("로그인이 되어 있지않습니다.");
				return;
			}

			System.out.println("로그아웃이 완료되었습니다.");

		} else if (command.equals("member info")) {
			System.out.println("== 로그인 사용자 정보 ==");

			MemberDTO member = memberService.memberWhoami();

			if (member == null) {
				System.out.println("로그인 후 이용해주시기 바랍니다.");
				return;
			}

			System.out.printf("ID : %s\n", member.memberId);
			System.out.printf("PW : %s\n", member.pw);
			System.out.printf("NAME : %s\n", member.name);

		} else {
			System.out.println("잘못입력하셨습니다.");
			return;
		}
	}
}