package com.sbs.test.textboard.service;

import java.util.List;

import com.sbs.test.textboard.container.Container;
import com.sbs.test.textboard.dao.MemberDao;
import com.sbs.test.textboard.dto.Member;

public class MemberService {
	private MemberDao memberDao;

	public MemberService() {
		memberDao = Container.memberDao;
	}

	public List<Member> getMembers() {
		return memberDao.getMembers();
	}

	public int memberJoin(String memberId, String pw, String name) {
		return memberDao.memberJoin(memberId, pw, name);
	}

	public int memberLogin(String memberId, String pw) {
		return memberDao.memberLogin(memberId, pw);
	}

	public Member memberWhoami() {
		return memberDao.memberWhoami();
	}

	public int memberLogout() {
		return memberDao.memberLogout();
	}
}
