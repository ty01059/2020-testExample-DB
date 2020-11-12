package com.sbs.test.mysqltextboard;

import java.util.Scanner;

import com.sbs.test.mysqltextboard.contoller.Controller;

public class App {
	private Controller controller;
	private Scanner sc;

	public App() {
		controller = new Controller();
		sc = new Scanner(System.in);
	}

	public void run() {

		while (true) {
			System.out.printf("명령어 : ");
			String cmd = sc.nextLine();

			if (cmd.equals("article list")) {
				controller.doCmd();
			} else if (cmd.equals("system exit")) {
				break;
			} else {
				continue;
			}
		}
	}
}
