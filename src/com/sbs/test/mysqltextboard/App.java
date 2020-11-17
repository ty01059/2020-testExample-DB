package com.sbs.test.mysqltextboard;

import java.util.Scanner;

import com.sbs.test.mysqltextboard.container.Container;
import com.sbs.test.mysqltextboard.contoller.ArticleController;
import com.sbs.test.mysqltextboard.contoller.Controller;
import com.sbs.test.mysqltextboard.contoller.MemberController;
import com.sbs.test.mysqltextboard.mysqlutil.MysqlUtil;

public class App {
	private ArticleController articleController;
	private MemberController memberController;
	private Scanner sc;

	public App() {
		articleController = Container.articleController;
		memberController = Container.memberController;
		sc = Container.scanner;
	}

	public void run() {
		while (true) {
			System.out.printf("명령어 : ");
			String cmd = sc.nextLine();

			Controller controller = getControllerByCmd(cmd);

			if (controller != null) {
				MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123", "a1");
				MysqlUtil.setDevMode(true);

				controller.doCmd(cmd);

				MysqlUtil.closeConnection();
			} else if (cmd.equals("system exit")) {
				System.out.println("== 프로그램 종료 ==");
				break;
			} else {
				continue;
			}
		}
	}

	private Controller getControllerByCmd(String cmd) {
		if (cmd.startsWith("article")) {
			return articleController;
		} else if (cmd.startsWith("member")) {
			return memberController;
		}
		return null;
	}
}
