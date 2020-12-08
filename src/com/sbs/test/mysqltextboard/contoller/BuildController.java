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
		} else if (keyword.equals("startAutoSite")) {
			StartAutoSite();
		} else if (keyword.equals("stopAutoSite")) {
			StopAutoSite();
		}
	}

	private void doBuildSite() {
		System.out.println("== html 생성을 시작합니다. ==");
		buildService.buildSite();
	}

	private void StartAutoSite() {
		System.out.println("== 자동 빌드 시작 ==");
		buildService.StartAutoSite();
	}

	private void StopAutoSite() {
		System.out.println("== 자동 빌드 정지 ==");
		buildService.StopAutoSite();
	}
}
