package com.br.recycle.api.commons;

/**
 * Classe responsável por conter as URIs da aplicação.
 * 
 * @author Caio Henrique do Carmo Bastos
 * @since 04/07/2021
 *
 */
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

	public static final String URI_BASE_ACCESS = "/api/v1/address";
	public static final String URI_ADDRESS_ID_USER = "/api/v1/address/";
	public static final String URI_ACCESS_ZIPCODE = "/zip_code/{zipCode}";
	public static final String URI_REVENUE_CNPJ = "/revenue/{cnpj}";
	public static final String URI_ACCESS_ID = "/{id}";
	public static final String URI_ZIP_CORREIO = "https://viacep.com.br/ws/";
	
	public static final String URI_CNPJ = "https://www.receitaws.com.br/v1/";
	
	public static final String URI_BASE_GIVER = "/api/v1/giver";
	public static final String URI_GIVER_ID = "/{id}";
}