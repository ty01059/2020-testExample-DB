package com.sbs.test.textboard.dao;

import java.util.ArrayList;
import java.util.List;

import com.sbs.test.textboard.container.Container;
import com.sbs.test.textboard.dto.Member;

public class MemberDao {

	private List<Member> members;
	private int joinNum;

	public MemberDao() {
		members = new ArrayList<Member>();
		joinNum = 0;

		// testMember
		memberJoin("aa", "aa", "aa");
		memberJoin("bb", "bb", "bb");
	}

	public List<Member> getMembers() {
		return members;
	}

	public int memberJoin(String memberId, String pw, String name) {

		for (Member member : members) {
			if (member.memberId.equals(memberId)) {
				return -1;
			}
		}

		Member newMember = new Member();
		newMember.id = joinNum + 1;
		newMember.memberId = memberId;
		newMember.pw = pw;
		newMember.name = name;

		members.add(newMember);
		joinNum++;

		return 0;
	}

	public int memberLogin(String memberId, String pw) {

		for (Member member : members) {
			if (member.memberId.equals(memberId) && member.pw.equals(pw)) {
				Container.session.setLogined(true);
				Container.session.setLoginUser(member);
				return 1;
			}
		}
		return -1;
	}

	public Member memberWhoami() {
		if (!Container.session.getLogined()) {
			return null;
		}
		return Container.session.getLoginUser();
	}

	public int memberLogout() {

		if (!Container.session.getLogined()) {
			return -1;
		}

		Container.session.setLogined(false);
		Container.session.setLoginUser(null);

		return 0;
	}
}
