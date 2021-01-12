package br.com.obpc.token;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.obpc.entities.User;
import br.com.obpc.exceptions.TokenForbiddenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;

@Service
public class JwtTokenHelper implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final String JWT_ISSUER = "OBPC_API";
	private static final long DEFAULT_TOKEN_EXPIRY_TIME = 3600;
	
	public String getAccessToken(User user) {
		return Jwts.builder()
				.setExpiration(expireTimeFromNow())
				.setIssuer(JWT_ISSUER)
				.setClaims(createClaims(user))
				.signWith(SignatureAlgorithm.HS512, user.getId()).compact();
	}
	
	private Date expireTimeFromNow() {
		return new Date(System.currentTimeMillis() + DEFAULT_TOKEN_EXPIRY_TIME * 1000);
	}
	
	public Optional<Void> validateToken(String token, String requesterId) throws Exception {
		Optional<Claims> fullToken = Optional.ofNullable(getTokenClaims(token, requesterId))
				.orElseThrow(()-> new TokenForbiddenException());
		
		Date expiration = fullToken.get().getExpiration();
		Date now = new Date();
		
		if(expiration.before(now))
				throw new TokenForbiddenException("Expired token");
				
		return Optional.empty();
	}
	
	public Optional<Claims> getTokenClaims(String token, String key) {
		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(key)
					.parseClaimsJws(token)
					.getBody()
					.setExpiration(expireTimeFromNow())
					.setIssuer(JWT_ISSUER)
					.setId(key);
					
		}catch(Exception e) {
			return null;
		}
		
		return Optional.of(claims);
	}
	
	public Claims createClaims(User user) {
		Claims claims = new DefaultClaims();
		claims.setIssuer(JWT_ISSUER);
		claims.setExpiration(expireTimeFromNow());
		claims.setId(user.getId());		
		return claims;
	}

}
