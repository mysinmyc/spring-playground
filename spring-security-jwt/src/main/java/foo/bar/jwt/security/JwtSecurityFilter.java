package foo.bar.jwt.security;

import java.io.IOException;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtSecurityFilter  extends OncePerRequestFilter{

	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	@Lazy
	UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	
	     final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
	        if (header ==null || !header.startsWith("Bearer ")) {
	        	filterChain.doFilter(request, response);
	            return;
	        }
	        
	        final String token = header.substring(7).trim();
	        if (!jwtTokenUtil.validateToken(token)) {
	            throw new AuthenticationException("Token expired");
	        }
	        
	        String username = jwtTokenUtil.getUsernameFromToken(token);
	        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

	        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        filterChain.doFilter(request, response);
	        
	}

}
