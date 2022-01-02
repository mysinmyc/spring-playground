package foo.bar.jwt.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil {

	@Value("${jwt.token.validityMs:3600000}")
	private long tokenValidityMs;
	
	@Value("${jwt.secret:LSj2DBQ0Jx0LdFopxcgeGoZxynntE3Fr0C1PqEzK+HxYrxsdxiiSmDxwAn8q2HcXHHxosD+6Mou+aoTE8OjYKg==}")
	private String secret;
	
	private Key getKey() {
		return    Keys.hmacShaKeyFor(secret.getBytes());

	}
	private JwtParser jwtParser() {
		return Jwts.parserBuilder().setSigningKey(getKey()).build();
	}
		
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims getAllClaimsFromToken(String token) {
		return jwtParser().parseClaimsJws(token).getBody();
	}
	
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}
	
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + tokenValidityMs))
				.signWith( getKey()).compact();
	}

	public Boolean validateToken(String token) {
		getUsernameFromToken(token);
		return !isTokenExpired(token);
	}
}
