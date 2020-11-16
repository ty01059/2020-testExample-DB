package com.sbs.test.mysqltextboard.service;

import java.util.Map;

import com.sbs.test.mysqltextboard.container.Container;
import com.sbs.test.mysqltextboard.dao.MemberDao;

public class MemberService {

	private MemberDao memberDao;

	public MemberService() {
		memberDao = Container.memberDao;
	}

	public Map<String, Object> getMember(int id) {
		return memberDao.getMember(id);
	}

	public int join(String id, String pw, String name) {
		return memberDao.join(id, pw, name);
	}

	public Map<String, Object> login(String id, String pw) {
		return memberDao.login(id, pw);
	}

	public void logout() {
		memberDao.logout();
	}
}
