package com.sbs.textboard;

import java.util.Scanner;

import com.sbs.textboard.container.Container;
import com.sbs.textboard.controller.ArticleController;
import com.sbs.textboard.controller.Controller;
import com.sbs.textboard.controller.MemberController;

public class App {
	Scanner sc;
	MemberController memberController;
	ArticleController articleController;

	App() {
		sc = Container.scanner;
		memberController = new MemberController();
		articleController = new ArticleController();

		while (true) {
			System.out.printf("명령어 : ");
			String command = sc.nextLine();

			Controller controller = getControllerByCmd(command);

			if (controller != null) {
				controller.doCommand(command);
			} else if (command.equals("system exit")) {
				System.out.println("== 프로그램 종료 ==");
				break;
			} else {
				System.out.println("등록된 명령어가 아닙니다.");
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
