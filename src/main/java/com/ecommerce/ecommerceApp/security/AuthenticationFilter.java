package com.ecommerce.ecommerceApp.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecommerce.ecommerceApp.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
	private AuthenticationManager authenticationManager;
	
	public AuthenticationFilter(AuthenticationManager authenticationManager)
	{
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException
	{
		try
		{
		    User crendential = new ObjectMapper().readValue(request.getInputStream(), User.class);
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(crendential.getUsername(), crendential.getPassword(), new ArrayList<>()));
		}
		catch(IOException e)
		{
			throw new RuntimeException("Could not read request" + e);
		}
	}
	
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) 
	{
		String token = Jwts.builder()
					   .setSubject(((org.springframework.security.core.userdetails.User)authentication.getPrincipal()).getUsername())
					   .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
					   .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET_KEY.getBytes())
					   .compact();
		response.addHeader(SecurityConstants.HEADER_AUTH_STRING, SecurityConstants.TOKEN_PREFIX + token);
	}
}
