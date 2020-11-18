package com.sbs.test.mysqltextboard.session;

import com.sbs.test.mysqltextboard.dto.Member;

public class Session {
	private boolean isLogined;
	private Member loginUser;

	private int selectBoardId;

	public boolean getLogined() {
		return isLogined;
	}

	public void setLogined(boolean isLogined) {
		this.isLogined = isLogined;
	}

	public Member getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(Member loginUser) {
		this.loginUser = loginUser;
	}

	public int getSelectBoardId() {
		return selectBoardId;
	}

	public void setSelectBoardId(int id) {
		this.selectBoardId = id;
	}
}
