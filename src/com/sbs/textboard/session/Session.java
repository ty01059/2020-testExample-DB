package com.sbs.textboard.session;

import com.sbs.textboard.dto.MemberDTO;

public class Session {
	private boolean isLogined;
	private MemberDTO loginUser;

	public boolean getIsLogined() {
		return isLogined;
	}

	public void setIsLogined(boolean isLogined) {
		this.isLogined = isLogined;
	}

	public MemberDTO getloginUser() {
		return loginUser;
	}

	public void setloginUser(MemberDTO loginUser) {
		this.loginUser = loginUser;
	}
}
