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

	public static final String URI_CONTACT = "/api/v1/contact";
	public static final String URI_BASE_ACCESS = "/api/v1/address";
	public static final String URI_ADDRESS_ID_USER = "/api/v1/address/";
	public static final String URI_ACCESS_ZIPCODE = "/zip_code/{zipCode}";
	public static final String URI_ACCESS_ID = "/{id}";
	public static final String URI_ZIP_CORREIO = "https://viacep.com.br/ws/";
	
	
	public static final String URI_BASE_GIVER = "/api/v1/giver";
	public static final String URI_GIVER_ID = "/{id}";
	
	public static final String URI_BASE_FLOW_DONATION = "/api/v1/donation/{code}";
	public static final String URI_FLOW_DONATION_CONFIRMED = "/confirmed";
	public static final String URI_FLOW_DONATION_CANCEL = "/cancel";
	public static final String URI_FLOW_DONATION_DELIVERY = "/deliver";	
	
	public static final String URI_BASE_DONATION = "/api/v1/donation";
	public static final String URI_DONATION_ID = "/{id}";
	
	public static final String URI_BASE_RATE = "/api/v1/rate";
	public static final String URI_RATE_ID = "/{id}";
	
	public static final String URI_BASE_COOPERATIVE = "/api/v1/cooperative";
	public static final String URI_COOPERATIVE_ID = "/{id}";
	public static final String URI_COOPERATIVE_CNPJ = "/cnpj/{cnpj}";

	public static final String URI_PROVIDER_CNPJ = "https://www.receitaws.com.br/v1/";
}