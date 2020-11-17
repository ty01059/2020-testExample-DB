package com.sbs.test.mysqltextboard.session;

import java.util.Map;

public class Session {
	private boolean isLogined;
	private Map<String, Object> loginUser;

	private int selectBoardId;

	public boolean getLogined() {
		return isLogined;
	}

	public void setLogined(boolean isLogined) {
		this.isLogined = isLogined;
	}

	public Map<String, Object> getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(Map<String, Object> loginUser) {
		this.loginUser = loginUser;
	}

	public int getSelectBoardId() {
		return selectBoardId;
	}

	public void setSelectBoardId(int id) {
		this.selectBoardId = id;
	}
}
