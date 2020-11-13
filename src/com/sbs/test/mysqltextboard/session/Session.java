package com.sbs.test.mysqltextboard.session;

import com.sbs.test.mysqltextboard.dto.Member;

public class Session {
	private boolean isLogined;
	private Member loginUser;

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
}
