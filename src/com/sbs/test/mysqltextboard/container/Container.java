package com.sbs.test.mysqltextboard.container;

import java.util.Scanner;

import com.sbs.test.mysqltextboard.contoller.ArticleController;
import com.sbs.test.mysqltextboard.contoller.MemberController;
import com.sbs.test.mysqltextboard.dao.ArticleDao;
import com.sbs.test.mysqltextboard.dao.MemberDao;
import com.sbs.test.mysqltextboard.service.ArticleService;
import com.sbs.test.mysqltextboard.service.MemberService;
import com.sbs.test.mysqltextboard.session.Session;

public class Container {
	public static Session session;
	public static Scanner scanner;

	public static MemberDao memberDao;
	public static MemberService memberSerivce;
	public static MemberController memberController;

	public static ArticleDao articleDao;
	public static ArticleService aritlceSerivce;
	public static ArticleController articleController;

	static {
		session = new Session();
		scanner = new Scanner(System.in);

		memberDao = new MemberDao();
		memberSerivce = new MemberService();
		memberController = new MemberController();

		articleDao = new ArticleDao();
		aritlceSerivce = new ArticleService();
		articleController = new ArticleController();
	}
}
