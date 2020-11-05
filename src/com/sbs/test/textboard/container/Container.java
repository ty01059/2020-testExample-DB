package com.sbs.test.textboard.container;

import java.util.Scanner;

import com.sbs.test.textboard.dao.ArticleDao;
import com.sbs.test.textboard.dao.MemberDao;
import com.sbs.test.textboard.service.ArticleService;
import com.sbs.test.textboard.service.MemberService;
import com.sbs.test.textboard.session.Session;

public class Container {
	public static Scanner scanner;
	public static Session session;

	public static ArticleService articleService;
	public static ArticleDao articleDao;

	public static MemberService memberService;
	public static MemberDao memberDao;

	static {
		scanner = new Scanner(System.in);
		session = new Session();

		articleService = new ArticleService();
		articleDao = new ArticleDao();

		memberService = new MemberService();
		memberDao = new MemberDao();
	}
}
