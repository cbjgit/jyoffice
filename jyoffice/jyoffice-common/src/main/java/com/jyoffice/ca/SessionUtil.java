package com.jyoffice.ca;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {

	public static String SESSION_LOGIN_KEY = "jyoffice_login";
	public static String SESSION_MENU_KEY = "jyoffice_user_menu";
	public static String SESSION_AUTH_KEY = "jyoffice_user_auth";

	public static IUser getUser(HttpServletRequest request) {
		IUser user = (IUser)request.getSession().getAttribute(SESSION_LOGIN_KEY);
		return user;
	}
	public static String getUserId(HttpServletRequest request) {
		IUser user = (IUser)request.getSession().getAttribute(SESSION_LOGIN_KEY);
		if(user != null)
			return user.getLoginId();
		return null;
	}
}
