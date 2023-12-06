package com.exciting.login.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.exciting.login.dto.MemberDTO;
import com.exciting.login.entity.Member;
import com.exciting.login.persistence.LoginRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OAuthUserServiceImpl extends DefaultOAuth2UserService{
	
	@Autowired
	private LoginRepository loginRepository;
	
	public OAuthUserServiceImpl() {
		super();
	}
	
	
//	인가를 마치고 발급된 토큰으로 user-info-uri에 지정된 주소를 호출해 사용자 정보를 가져운다(OAuth2UserRequest 에 담겨 있다) 이 정보를 이용해 사용자의 계정이 우리 memberDB이미 등록되어 있는 아이디인지 아닌지 확인한다.
//  등록되지 않은 계정이라면 새로 등록한다.
//  원래 매서드인 loadUser의 작업은 그대로 진행하고 덧붙혀서 member_id를 가져와 registrationId별로 커스텀 작업하는 것을 추가했다.
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
		
		// DefualtOauth2Userservice의 기존 loadUser를 호출한다. 이메서드가 super-info-uri를 이용해 사용자 정보를 가져오는 부분이다.(OAuth2user에 유저정보가담겨있다)
		final OAuth2User oAuth2User = super.loadUser(userRequest);
		try {
			// 사용자 정보 로깅(테스트 시에만)
			log.info("OAuth2User attributes {} ", new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		// github,kakao,naver에 따라 분기
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("registrationId {} ", registrationId );
                
        if(registrationId.equals("github")) {
        	
        	// 사용자가 로그인을 하고 인가를 허락하면 github측에서 login필드를 반환하고, 이 login필드에서 사용자의 github 정보를 가져온다.
        	final String m_github_id = (String)oAuth2User.getAttributes().get("login");
        	final String member_id = "github_"+m_github_id;
        	
        	Member memberEntity = null;
        	// member table에 유저가 존재하지 않으면 새롭게 등록 
        	if(!loginRepository.existsByM_github_id(m_github_id)) {
        		System.err.println("OAuthUserServiceImpl/loadUser/ loginRepository / existsByM_github_id / membertable에 m_github_id 존재안함");
        		memberEntity = Member.builder()
        				.member_id(member_id)
        				.m_github_id(m_github_id)
        				.roles("ROLE_user")
        				.build();
        		
        		memberEntity = loginRepository.save(memberEntity);
        	}
        	
        	log.info("Successfully pulled user info member_id {} authProvider {}",member_id,m_github_id);
        	return new ApplicationOAuth2User(member_id,oAuth2User.getAttributes());
        	
        }else if(registrationId.equals("kakao")) {
        	
        	log.info("registrationId == kakao");
        	// 사용자가 로그인을 하고 인가를 허락하면 kakao측에서 id필드를 반환하고, 이 id필드에서 사용자의 kakao id(고유번호)를 가져온다.
        	final String m_kakao_id = String.valueOf(oAuth2User.getAttributes().get("id"));
        	final String member_id = "kakao_"+m_kakao_id;
        	log.info("m_kakao_id {}", m_kakao_id);
        	
        	Member memberEntity = null;
        	// member table에 유저가 존재하지 않으면 새롭게 등록 
        	if(!loginRepository.existsByM_kakao_id(m_kakao_id)) {
        		System.err.println("OAuthUserServiceImpl/loadUser/ loginRepository / existsByM_kakao_id / membertable에 m_kakao_id 존재안함");
        		memberEntity = Member.builder()
        				.member_id(member_id)
        				.m_kakao_id(m_kakao_id)
        				.roles("ROLE_user")
        				.build();
        		
        		memberEntity = loginRepository.save(memberEntity);
        	}
        	
        	log.info("Successfully pulled user info member_id {} authProvider {}",member_id,m_kakao_id);
        	return new ApplicationOAuth2User(member_id,oAuth2User.getAttributes());
        	
        }else if(registrationId.equals("naver")){
        	
        	// 사용자가 로그인을 하고 인가를 허락하면 naver측에서 id필드를 반환하고, 이 id필드에서 사용자의 naver id(고유번호)를 가져온다.
        	final String m_naver_id = String.valueOf(oAuth2User.getAttributes().get("id"));
        	final String member_id = "naver_"+m_naver_id;
        	
        	Member memberEntity = null;
        	// member table에 유저가 존재하지 않으면 새롭게 등록 
        	if(!loginRepository.existsByM_naver_id(m_naver_id)) {
        		System.err.println("OAuthUserServiceImpl/loadUser/ loginRepository / existsByM_kakao_id / membertable에 m_naver_id 존재안함");
        		memberEntity = Member.builder()
        				.member_id(member_id)
        				.m_kakao_id(m_naver_id)
        				.roles("ROLE_user")
        				.build();
        		
        		memberEntity = loginRepository.save(memberEntity);
        	}
        	
        	log.info("Successfully pulled user info member_id {} authProvider {}",member_id,m_naver_id);
        	return new ApplicationOAuth2User(member_id,oAuth2User.getAttributes());
        	
        }else {
        	log.info("registrationId is Null...");
        	return null;
        }
        
		
		
//		final String authProvider = userRequest.getClientRegistration().getClientName();
		
		
	}
}
