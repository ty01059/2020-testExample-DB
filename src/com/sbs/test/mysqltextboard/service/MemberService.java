package com.sbs.test.mysqltextboard.service;

import com.sbs.test.mysqltextboard.container.Container;
import com.sbs.test.mysqltextboard.dao.MemberDao;
import com.sbs.test.mysqltextboard.dto.Member;

public class MemberService {

	private MemberDao memberDao;

	public MemberService() {
		memberDao = Container.memberDao;
	}

	public Member getMemberId(String memberId) {
		return memberDao.getMemberId(memberId);
	}

	public int join(String memberId, String pw, String name) {
		return memberDao.join(memberId, pw, name);
	}

	public Member login(String memberId, String pw) {
		return memberDao.login(memberId, pw);
	}
}
