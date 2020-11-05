package com.sbs.test.textboard;

import java.util.Scanner;

import com.sbs.test.textboard.container.Container;
import com.sbs.test.textboard.controller.ArticleController;
import com.sbs.test.textboard.controller.Controller;
import com.sbs.test.textboard.controller.MemberController;

public class App {
	Scanner sc;
	MemberController memberController;
	ArticleController articleController;

	App() {
		sc = Container.scanner;
		memberController = new MemberController();
		articleController = new ArticleController();
		run();
	}

	void run() {
		while (true) {
			System.out.printf("명령어 : ");
			String command = sc.nextLine();

			Controller controller = getControllerByCmd(command);
			if (controller != null) {
				controller.doCommand(command);
			} else if (command.startsWith("system exit")) {
				System.out.println("== 프로그램 종료 ==");
				break;
			} else {
				System.out.println("잘못입력하셨습니다.");
				continue;
			}
		}
		sc.close();
	}

	private Controller getControllerByCmd(String command) {
		if (command.startsWith("member")) {
			return memberController;
		} else if (command.startsWith("article")) {
			return articleController;
		}

		return null;
	}
}
