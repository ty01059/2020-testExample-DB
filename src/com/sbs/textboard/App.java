package com.sbs.textboard;

import java.util.Scanner;

import com.sbs.textboard.controller.ArticleController;
import com.sbs.textboard.controller.MemberController;

public class App {
	Scanner sc;
	MemberController memberController;
	ArticleController articleController;

	App() {
		sc = new Scanner(System.in);
		memberController = new MemberController();
		articleController = new ArticleController();

		while (true) {
			System.out.printf("명령어 : ");
			String command = sc.nextLine();

			if (command.equals("system exit")) {
				System.out.println("== 프로그램 종료 ==");
				break;
			} else if (command.startsWith("member")) {
				memberController.run(sc, command);
			} else if (command.startsWith("article")) {
				articleController.run(sc, command);
			} else {
				System.out.println("등록된 명령어가 아닙니다.");
				continue;
			}
		}

		sc.close();
	}
}
