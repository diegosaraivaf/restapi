package com.projeto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.projeto.service.SecurityUserDetailsService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private SecurityUserDetailsService securityUserDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	//faz a autenticacao de usuario
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		String senhaCodificada = passwordEncoder().encode("123");
//		
//		auth
//			.inMemoryAuthentication()
//			.withUser("usuario")
//			.password(senhaCodificada)
//			.roles("USER");
		
		auth
			.userDetailsService(securityUserDetailsService)
			.passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers(HttpMethod.POST,"/usuarios/autenticar").permitAll()
				.antMatchers(HttpMethod.POST,"/usuarios/autenticar/").permitAll()
				.antMatchers(HttpMethod.POST,"/usuarios").permitAll()
				.antMatchers(HttpMethod.POST,"/usuarios/").permitAll()
				.anyRequest().authenticated()
		.and()
			//impede que o spring crie sessoes
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
			.httpBasic();
	}

}
