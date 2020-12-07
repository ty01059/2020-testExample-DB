package com.sbs.test.mysqltextboard.container;

import java.util.Scanner;

import com.sbs.test.mysqltextboard.contoller.ArticleController;
import com.sbs.test.mysqltextboard.contoller.BuildController;
import com.sbs.test.mysqltextboard.contoller.MemberController;
import com.sbs.test.mysqltextboard.dao.ArticleDao;
import com.sbs.test.mysqltextboard.dao.MemberDao;
import com.sbs.test.mysqltextboard.service.ArticleService;
import com.sbs.test.mysqltextboard.service.BuildService;
import com.sbs.test.mysqltextboard.service.MemberService;
import com.sbs.test.mysqltextboard.session.Session;

public class Container {
	public static Session session;
	public static Scanner scanner;

	public static MemberDao memberDao;
	public static ArticleDao articleDao;
	public static MemberService memberService;
	public static ArticleService articleService;
	public static BuildService buildService;
	public static MemberController memberController;
	public static ArticleController articleController;
	public static BuildController buildController;

	static {
		session = new Session();
		scanner = new Scanner(System.in);

		memberDao = new MemberDao();
		articleDao = new ArticleDao();
		memberService = new MemberService();
		articleService = new ArticleService();
		buildService = new BuildService();
		memberController = new MemberController();
		articleController = new ArticleController();
		buildController = new BuildController();
	}
}
