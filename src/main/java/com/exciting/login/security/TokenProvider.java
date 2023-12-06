package com.exciting.login.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.exciting.login.entity.Member;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenProvider {
	private static final String SECRET_KEY = "hellowmama";
	
//	사용자가 HTTP request요청을 보냈을때 사용자의 정보를 바탕으로 TOKEN을 만듦.
	public String create(Member memberEntity) {
		
//		기한 지금으로부터 1일로 설정
		Date expiryDate = Date.from(Instant.now().plus(1,ChronoUnit.DAYS));
		
		Claims claims = Jwts.claims().setSubject(memberEntity.getMember_id());
		claims.put("roles", memberEntity.getRoles());
		
//		Map<String,Object> map = new HashMap<>();
//		
//		map.put("roles", memberEntity.getRoles());
//		map.put("subject", memberEntity.getMember_id());
		
//		header와 payload를 secret_key로 해시알고리즘을 이용해서 암호화 하고
//		그값을 header와 payload와 연결해서 인코딩한다 -> TOKEN
		return Jwts.builder()
//				header에 들어갈 내용 및 서명을 하기 위한 SECRET_KEY
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
//				payLoad에 들어갈 내용(subject : 유저의 id)
//				나중에 @AuthenticationPrincipal 을 적용해서 인증을 할때 반환되는 값이된다.
//				.setSubject(memberEntity.getMember_id())
				.setClaims(claims)
				.setIssuer("exciting")
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)
				.compact();
		
	
	}
	
	
	
	public String create(final Authentication authentication) {
		
		ApplicationOAuth2User userPrincipal = (ApplicationOAuth2User)authentication.getPrincipal();
//		소셜 아이디로에 등록된 member_id
		System.out.println("TokenProvider / create / userPrincipal : "+userPrincipal.getName());
		
		Claims claims = Jwts.claims().setSubject(userPrincipal.getName());
		claims.put("roles", "ROLE_user");
		
		Date expiryDate = Date.from(Instant.now().plus(1,ChronoUnit.DAYS));
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.compact();
	
	}
	
//	사용자의 요청할때 보낸 TOKEN과 사용자의 정보를 이용해 만든 TOKEN이 일치하는지 비교. 일치하면 Claims 리턴
	public Claims validateAndGetUserId(String token) {
		
//		parseClaimsJws 메서드가 Base 64로 디코딩 및 파싱
//		즉, 헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해 서명 후, token의 서명과 비교
//		위조되지 않았따면 페이로드(Claims) 리턴, 위조라면 예외를 날림
//		그 중 우리는 userId가 필요하므로 getBody를 부른다.
		Claims claims = Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();
		
		System.out.println("claims : "+Jwts.parser()
		.setSigningKey(SECRET_KEY)
		.parseClaimsJws(token));
		System.out.println(claims);
				
//		create에서 setClaims로 대입하면서 setSubject()가 안먹혀 get SUbject()도 안됨
//		return claims.getSubject();
		return claims;
	}
}
