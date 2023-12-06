package com.exciting.login.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

// onceperrequestFilter 상속 : 각 요청에 대해 한번만 호출된다. 동일한 필터가 중복으로 실행되지 않는다.
// RedirectUrlCokkieFilter class : session을 생성해서 response에 저장한다. 
@Slf4j
@Component
public class RedirectUrlCookieFilter extends OncePerRequestFilter {
	
//	react단에서 쿼리 파라미터로 redirect_uri 를 보낸다. 그값을 읽어오는 것이다.
	public static final String REDIRECT_URI_PARAM = "redirect_uri";
	private static final int MAX_AGE = 180;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		if(request.getRequestURI().startsWith("/auth/authorize")) {
			try {
				log.info("request url {} ",request.getRequestURI());
//				리퀘스트 파라미터에서 redirect_url을 가져온다.
				String redirectUrl = request.getParameter(REDIRECT_URI_PARAM);
				System.out.println("RedirectUrlCookieFilter / redirectUrl / : "+redirectUrl);
				
				Cookie cookie = new Cookie(REDIRECT_URI_PARAM,redirectUrl);
				cookie.setPath("/");
				cookie.setHttpOnly(true);
				cookie.setMaxAge(MAX_AGE);
				response.addCookie(cookie);
			} catch (Exception ex) {
				logger.error("could not set user authentication in security context",ex);
				log.info("Unauthorized request");
			}
		}
//		filterChain에 의해 다음 필터로 전달된다.
		filterChain.doFilter(request, response);
	}
}
