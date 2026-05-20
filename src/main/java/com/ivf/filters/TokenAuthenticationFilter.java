package com.ivf.filters;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ivf.services.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {

			String authHeader = request.getHeader("Authorization");

			if (request.getMethod().equals("OPTIONS")|| request.getRequestURI().contains("/api/auth/login") || request.getRequestURI().startsWith("/ws")) {
				
				System.out.println("request.getRequestURI() : " + request.getRequestURI());

				filterChain.doFilter(request, response);
				return;
			}

			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return;
			}

			String token = authHeader.substring(7);

			String username = tokenService.extractUsername(token);
			String role = tokenService.extractRole(token);

			if (username != null && !tokenService.isTokenExpired(token) && SecurityContextHolder.getContext().getAuthentication() == null) {

				List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,authorities);

				SecurityContextHolder.getContext().setAuthentication(auth);
			}

			filterChain.doFilter(request, response);

		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
		}
	}
}
