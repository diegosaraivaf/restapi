package com.projeto.filtro;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.projeto.service.JwtService;
import com.projeto.service.SecurityUserDetailsService;

public class JwtTokenFilter extends OncePerRequestFilter{

	@Autowired
	private JwtService jwtService;

	@Autowired
	private SecurityUserDetailsService securityUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		MDC.put("tenantId", "teste");
		
		String autorization = request.getHeader("Authorization");
	
		if(autorization != null && autorization.startsWith("Bearer")){
			String token = autorization.split(" ")[1];
			boolean isTokenValid = jwtService.isTokenValido(token);
		
			if(isTokenValid){
				String login = jwtService.obterLoginUsuario(token);
				UserDetails usuarioAutenticado = securityUserDetailsService.loadUserByUsername(login);
			
				UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(
						usuarioAutenticado,null,usuarioAutenticado.getAuthorities()
				);
			
				user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(user);
			}
		}
		filterChain.doFilter(request,response);
		
		MDC.remove("tenantId");
	}
}
