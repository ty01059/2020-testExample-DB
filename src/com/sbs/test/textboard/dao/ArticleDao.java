package com.sbs.test.textboard.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.sbs.test.textboard.container.Container;
import com.sbs.test.textboard.dto.Article;
import com.sbs.test.textboard.dto.Member;
import com.sbs.test.textboard.service.MemberService;
import com.sbs.test.textboard.session.Session;

public class ArticleDao {

	private List<Article> articles;
	private MemberService memberservice;
	private Session session;

	private int articleId;

	public ArticleDao() {
		articles = new ArrayList<Article>();
		memberservice = Container.memberService;
		session = Container.session;

		articleId = 0;

		// test
		for (int i = 0; i < 5; i++) {
			articleAdd("aaa", "aaa", 1);
		}
		for (int i = 5; i < 10; i++) {
			articleAdd("bbb", "bbb", 2);
		}
	}

	private void articleAdd(String title, String body, int id) {

		Article newArticle = new Article();
		newArticle.id = articleId + 1;
		newArticle.writeNum = id;
		newArticle.title = title;
		newArticle.body = body;
		newArticle.regDate = getDate();

		articles.add(newArticle);
		articleId++;
	}

	private String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar time = Calendar.getInstance();
		return format.format(time.getTime());
	}

	public List<Article> getArticles() {
		return articles;
	}

	public int articleAdd(String title, String body) {

		Article newArticle = new Article();
		newArticle.id = articleId + 1;
		newArticle.writeNum = session.getLoginUser().id;
		newArticle.title = title;
		newArticle.body = body;
		newArticle.regDate = getDate();

		articles.add(newArticle);
		articleId++;
		return newArticle.id;
	}

	public String getWriter(int writeNum) {

		List<Member> members = new ArrayList<Member>();
		memberservice.getMembers();

		for (Member member : members) {
			if (member.id == writeNum) {
				return member.memberId;
			}
		}
		return null;
	}
}
