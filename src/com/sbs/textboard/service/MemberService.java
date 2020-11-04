package com.sbs.textboard.service;

import java.util.List;

import com.sbs.textboard.dao.MemberDAO;
import com.sbs.textboard.dto.MemberDTO;

public class MemberService {
	MemberDAO memberDAO;

	public MemberService() {
		memberDAO = new MemberDAO();
	}

	public int memberJoin(String memberId, String pw, String name) {
		return memberDAO.memberJoin(memberId, pw, name);
	}

	public int memberLogin(String memberId, String pw) {
		return memberDAO.memberLogin(memberId, pw);
	}

	public int memberLogout() {
		return memberDAO.memberLogout();
	}

	public MemberDTO memberWhoami() {
		return memberDAO.memberWhoami();
	}

	public List<MemberDTO> getMembers() {
		return memberDAO.getMembers();
	}
}