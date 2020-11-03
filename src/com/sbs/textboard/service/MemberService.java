package com.sbs.textboard.service;

import com.sbs.textboard.dao.MemberDAO;

public class MemberService {
	MemberDAO memberDAO;

	public MemberService() {
		memberDAO = new MemberDAO();
	}

	public int memberLogin(String id, String pw) {
		return memberDAO.memberLogin(id, pw);
	}
}