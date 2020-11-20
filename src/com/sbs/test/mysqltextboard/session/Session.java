package com.sbs.test.mysqltextboard.session;

import com.sbs.test.mysqltextboard.dto.Member;

public class Session {
	private boolean isLogined;
	private Member isLoginMember;

	private int isBoardId;

	public boolean getLogined() {
		return isLogined;
	}

	public void setLogined(boolean isLogined) {
		this.isLogined = isLogined;
	}

	public Member getLoginMember() {
		return isLoginMember;
	}

	public void setLoginMember(Member isLoginMember) {
		this.isLoginMember = isLoginMember;
	}

	public int getBoardId() {
		return isBoardId;
	}

	public void setBoardId(int isBoardId) {
		this.isBoardId = isBoardId;
	}
}
