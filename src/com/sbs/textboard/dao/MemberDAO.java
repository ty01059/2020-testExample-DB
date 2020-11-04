package com.sbs.textboard.dao;

import java.util.ArrayList;
import java.util.List;

import com.sbs.textboard.container.Container;
import com.sbs.textboard.dto.MemberDTO;
import com.sbs.textboard.session.Session;

public class MemberDAO {

	private List<MemberDTO> members;
	private Session session;
	private int memberNum;

	public MemberDAO() {
		members = new ArrayList<MemberDTO>();
		session = Container.session;
		memberNum = 0;

		// test 계정 생성
		memberJoin("aa", "aa", "aa");
	}

	public int memberJoin(String memberId, String pw, String name) {

		for (int i = 0; i < members.size(); i++) {
			if (members.get(i).memberId.equals(memberId)) {
				return -1;
			}

			if (members.get(i).name.equals(name)) {
				return -2;
			}
		}

		MemberDTO newMember = new MemberDTO();
		newMember.id = memberNum + 1;
		newMember.memberId = memberId;
		newMember.pw = pw;
		newMember.name = name;

		members.add(newMember);
		memberNum++;

		return 1;
	}

	public int memberLogin(String memberId, String pw) {

		if (memberId.equals("") || pw.equals("")) {
			return -1;
		}

		for (int i = 0; i < members.size(); i++) {
			if (members.get(i).memberId.equals(memberId) && members.get(i).pw.equals(pw)) {
				session.setloginUser(members.get(i));
				session.setIsLogined(true);
				return 1;
			}
		}
		return 0;
	}

	public int memberLogout() {

		if (!session.getIsLogined()) {
			return -1;
		}

		session.setloginUser(null);
		session.setIsLogined(false);
		return 1;
	}

	public MemberDTO memberWhoami() {

		if (!session.getIsLogined()) {
			return null;
		}
		return session.getloginUser();
	}

	public List<MemberDTO> getMembers() {
		return members;
	}
}