package com.sbs.test.mysqltextboard.contoller;

import com.sbs.test.mysqltextboard.service.BuildService;

public class BuildController extends Controller {

	private BuildService buildService;

	public BuildController() {
		buildService = new BuildService();
	}

	public void doCmd(String cmd) {
		if (cmd.split(" ").length < 2) {
			return;
		}

		String keyword = cmd.split(" ")[1];

		if (keyword.equals("site")) {
			doBuildSite();
		}
	}

	private void doBuildSite() {
		System.out.println("== html 생성을 시작합니다. ==");
		buildService.buildSite();
	}
}
