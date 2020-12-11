package com.ecommerce.ecommerceApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter
{	
	private UserDetailsService userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() 
	{
	    return new BCryptPasswordEncoder();
	}
	
	public WebSecurityConfiguration(UserDetailsService userDetailsService)
    {
        this.userDetailsService = userDetailsService;
    }
	
	protected void configure(HttpSecurity httpSecurity) throws Exception
	{
		httpSecurity.cors().and().csrf().disable().authorizeRequests()
		            .antMatchers("/h2/**").permitAll()
		            .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()	            
		            .anyRequest().authenticated()
		            .and().addFilter(new AuthenticationFilter(authenticationManager()))
		            .addFilter(new AuthorizationFilter(authenticationManager()))
		            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		// to use h2 web console
		httpSecurity.headers().frameOptions().disable();
	}
	
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception
	{
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource()
	{
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
}
