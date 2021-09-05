package com.projeto.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.projeto.filtro.JwtTokenFilter;
import com.projeto.service.JwtService;
import com.projeto.service.SecurityUserDetailsService;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private SecurityUserDetailsService securityUserDetailsService;
	
	@Autowired
	private JwtService jwtService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	@Bean
	public JwtTokenFilter jwtTokenFilter(){
//     na implementacao do video ele passar as implementacoes por parametro, ver se desta forma a instaceia consegue iniciar os atributos
//		return new JwtTokenFilter(jwtService,securityUserDetailsService);
		return new JwtTokenFilter();
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
			//.httpBasic()
			.addFilterBefore(jwtTokenFilter(),UsernamePasswordAuthenticationFilter.class);
		;
	}
	
	//este filter parece nao esta sendo utilizado 
	//na video aula ele e utilizado para solucionar um erro de cors,
	//mas neste projeto nao esta dando problema
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		List<String> all = Arrays.asList("*");

		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedMethods(all);
		config.setAllowedOrigins(all);
		config.setAllowedHeaders(all);
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**",config);
		
		CorsFilter corFilter = new CorsFilter(source);

		FilterRegistrationBean<CorsFilter> filter =
				new FilterRegistrationBean<CorsFilter>(corFilter);
		filter.setOrder(Ordered.HIGHEST_PRECEDENCE);

		return filter;
	}

}
