package com.sbs.textboard.dao;

import java.util.ArrayList;
import java.util.List;

import com.sbs.textboard.container.Container;
import com.sbs.textboard.dto.MemberDTO;
import com.sbs.textboard.session.Session;

public class MemberDAO {

	private List<MemberDTO> members;
	private Session session;

	public MemberDAO() {
		members = new ArrayList<MemberDTO>();
		session = Container.session;

		// test 계정 생성
		for (int i = 0; i < 3; i++) {
			MemberDTO newMember = new MemberDTO();
			newMember.id = "id" + i;
			newMember.pw = "id" + i;
			newMember.name = "id" + i;

			members.add(newMember);
		}
	}

	public int memberLogin(String id, String pw) {

		if (id.equals("") || pw.equals("")) {
			return -1;
		}

		for (int i = 0; i < members.size(); i++) {
			if (members.get(i).id.equals(id) && members.get(i).pw.equals(pw)) {
				session.setloginUser(members.get(i));
				session.setIsLogined(true);
				return 1;
			}
		}
		return 0;
	}
}