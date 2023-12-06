package com.exciting.login.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.exciting.login.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// SimpleUrlAuthenticationSuccessHandler : Oauth 인증과정이 모두 끝난 후 반환되는 클래스(token까지 발급이 됨)
// 발급된 토큰을 응답객체에 담아서 응답전송하는 매서드
@Slf4j
@Component
@AllArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	private static final String LOCAL_REDIRECT_URL = "http://localhost:3000";
	
	private RedirectUrlCookieFilter cookieFilter;
	
	@Autowired
	LoginService loginService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
//		인증이 완료된 후 반환된 Authentication의 값을 통해서 token을 생성한다.
		log.info("auth succeeded");
		TokenProvider tokenProvider = new TokenProvider();
		String token = tokenProvider.create(authentication);
		
		ApplicationOAuth2User getPrincipal = (ApplicationOAuth2User)authentication.getPrincipal();
//		response 객체에 token을 저정한다.(응답 HTTP 객체에 전송된다.)
		log.info("onAuthenticationSuccess / authentication/ name {} ", getPrincipal.getName());
		log.info("onAuthenticationSuccess / authentication/ Authorities {} ", getPrincipal.getAuthorities());
		log.info("onAuthenticationSuccess / authentication/ Class {} ", getPrincipal.getClass());
		
		Optional<Cookie> oCookie = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals(cookieFilter.REDIRECT_URI_PARAM)).findFirst(); 
		Optional<String> redirectUri = oCookie.map(Cookie::getValue);
		log.info("token {}",token);
		
		String registrationId = "";
		if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            registrationId =  oauthToken.getAuthorizedClientRegistrationId();
		}
		
		int result;
		
		if(registrationId.equals("github")) {
			
			log.info("onAuthenticationSuccess / authentication/ id {} ", getPrincipal.getAttributes().get("login"));
//			m_git_id 행의 m_name이 null(회원가입 x) 이면 0 null이 아니면(회원가입 o) 1 
			result = loginService.getNameByM_github_id((String)getPrincipal.getAttributes().get("login"));
			log.info("회원가입 여부(0:안함, 1: 함, 2:registrationId is Null) {} ", result);
			response.sendRedirect(redirectUri.orElseGet(()-> LOCAL_REDIRECT_URL) + "/githubLogin?token="+token+"&member_id="+getPrincipal.getName()+"&git_id="+getPrincipal.getAttributes().get("login")+"&result="+result);
			
		}else if(registrationId.equals("kakao")) {
			
			log.info("onAuthenticationSuccess / authentication/ id {} ", getPrincipal.getAttributes().get("id"));
			result = loginService.getNameByM_kakao_id(String.valueOf(getPrincipal.getAttributes().get("id")));
			log.info("회원가입 여부(0:안함, 1: 함, 2:registrationId is Null) {} ", result);
			response.sendRedirect(redirectUri.orElseGet(()-> LOCAL_REDIRECT_URL) + "/kakaoLogin?token="+token+"&member_id="+getPrincipal.getName()+"&kakao_id="+getPrincipal.getAttributes().get("id")+"&result="+result);

			
		}else if(registrationId.equals("naver")) {
			
			log.info("onAuthenticationSuccess / authentication/ id {} ", getPrincipal.getAttributes().get("id"));
			result = loginService.getNameByM_naver_id(String.valueOf(getPrincipal.getAttributes().get("login")));
			log.info("회원가입 여부(0:안함, 1: 함, 2:registrationId is Null) {} ", result);
			response.sendRedirect(redirectUri.orElseGet(()-> LOCAL_REDIRECT_URL) + "/naverLogin?token="+token+"&member_id="+getPrincipal.getName()+"&naver_id="+getPrincipal.getAttributes().get("login")+"&result="+result);

			
		}else {
			
			log.info("registrationId is Null ");
			result = 2;
			log.info("회원가입 여부(0:안함, 1: 함, 2:registrationId is Null) {} ", result);
			response.sendRedirect(redirectUri.orElseGet(()-> LOCAL_REDIRECT_URL) + "/errorLogin");

		}
		
	}
}
