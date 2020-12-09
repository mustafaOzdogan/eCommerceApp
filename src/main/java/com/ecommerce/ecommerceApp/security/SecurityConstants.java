package com.ecommerce.ecommerceApp.security;

public class SecurityConstants 
{
	public static final String SIGN_UP_URL = "/signup";
    public static final String SECRET_KEY = "eCommerceSecretKey";
    public static final long EXPIRATION_TIME = 432_000_000; // 5 gün
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_AUTH_STRING = "Authorization";
}
