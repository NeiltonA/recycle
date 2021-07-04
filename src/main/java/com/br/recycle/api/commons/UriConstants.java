package com.br.recycle.api.commons;

public final class UriConstants {
	
	public static final String URI_BASE_AUTH = "/api/v1/auth";
	public static final String URI_AUTH_ACCESS = "/access";
	public static final String URI_AUTH_REFRESH = "/refresh";
	public static final String URI_AUTH_LOGOUT = "/logout";
	
	public static final String URI_BASE_USER = "/api/v1/user";
	public static final String URI_USER_ID = "/{id}";
	public static final String URI_USER_UPDATE_PASSWORD = "/{id}/password";
	public static final String URI_USER_FORGOT_PASSWORD = "/forgot-password";
	public static final String URI_USER_RESET_PASSWORD = "/reset-password";
}
