package com.exciting.login.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.filter.CorsFilter;

import com.exciting.login.security.JwtAuthenticationFilter;
import com.exciting.login.security.OAuthSuccessHandler;
import com.exciting.login.security.OAuthUserServiceImpl;
import com.exciting.login.security.RedirectUrlCookieFilter;

import lombok.extern.slf4j.Slf4j;

// 작성한 필터(JwtAuthenticationFilter)를 스프링 시큐리티가 사용하라고 알려주는 작업
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig {
	
//	@Autowired
//	private ClientRegistrationRepository repo;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private OAuthUserServiceImpl oAuthUserService;
	
	@Autowired
	private OAuthSuccessHandler oAuthSuccessHandler;
	
	@Autowired
	private RedirectUrlCookieFilter redirectUrlFilter;
	 
//    @Bean
//    public OAuth2AuthorizationRequestResolver customAuthorizationRequestResolver() {
//      
//        return new CustomAuthorizationRequestResolver(repo,"/auth/authorize");
//    }
//	
//	@Autowired
//	@Lazy
//	private OAuth2AuthorizationRequestResolver customAuthorizationRequestResolver;
	
//	HttpSecurity : 시큐리티 설정을 위한 오브젝트(web.xml 대신 HttpSecurity를 이용해 시큐리티 관련 설정을 하는것)
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//		http 시큐리티 빌더
		  http.cors() //WebMvConfig에서 이미 설정했으므로 기본 cors 설정.
			.and()
			.csrf() //csrf는 현재 사용하지 않으므로 disable
			.disable()
			.httpBasic() //token을 사용하므로 basic 인증 disable
			.disable()
			.sessionManagement() // session 기반이 아님을 선언
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests() // /와 /login/** 경로는 인증 안 해도 됨.
			.mvcMatchers("/").permitAll()
			.mvcMatchers("/login/**","/email","/mypage/getMemberByKakaoId").permitAll()
			.mvcMatchers("/auth/**","/login/authGit/**","/mypage/editMember").permitAll()
			.mvcMatchers("/amusement/**","/rideDetail/**","/list/**","/selectedapi/**").permitAll()
			.mvcMatchers("/mypage/getMember","/mypage/upload","/mypage/getList","/mypage/writeList/*","/mypage/pastPost/*","/mypage/byebye")
			.hasAnyRole("user","admin")
			.mvcMatchers("/board/**","/customer/**","/upload/**").permitAll()
			.mvcMatchers("/promotion/**", "/promotionprice/**", "/wishList/**", "/mypoint/**", "/writeList/**", "/order/**", "/orderlist/**", "/check/**", "/refund/**").permitAll()
			.anyRequest() // antMatchers를 제외한 모든 API
			.authenticated()// token 인증이 있어야 함, 역할 까지는 필요 없음
		    .and()
//		    [[Oauth2 인증시작]]
		    .oauth2Login()
		    .redirectionEndpoint() // 리디렉션 주소 설정
//			baseUri()+registrationId 형태로 인식해 제공자별 redirectUri 주소를 다르게 적용시켜 줄수 있다.
		    .baseUri("/login/auth/*")
		    .and()
		    .authorizationEndpoint() //인가과정 시작주소 설정
//		    spring security의 기본설정은 DefaultOAuth2AuthorizationRequestResolver class 가 수행하는데, 이를 custom한
//		    customAuthorizationRequestResolver class가 대신하게 한다.
//		    .authorizationRequestResolver(customAuthorizationRequestResolver)
//		    윗줄에서 custom하지 않았다면 clientserver에서 인가과정을 시작하려 backend서버로 요청한 주소가 baseUri()에 등록된 주소 + registrationId
//		    의 구조로 DefaultOAuth2AuthorizationRequestResolver 에서 인식한다. registrationId에 따라 github,kakao등 제공자를 구분할수 있다.
//		    DefaultOAuth2AuthorizationRequestResolver class에서 authorizeUri를 "baseUri()+/{registrationId}"
//		    형식으로 설정할시 registrationId에 맞춰 properties에 있는 설정들이 구분되어 적용된다. registrationId 예) github,kakao
		    .baseUri("/auth/authorize") 
		    .and()
		    .userInfoEndpoint()
		    .userService(oAuthUserService) // 유저정보 가지고 오는 class 등록
		    .and() 
		    .successHandler(oAuthSuccessHandler) //token 까지 git서버에서 전송되고(Oauth인증과정이 마무리된후) 토큰을 reponse 객체에 담는 클래스
		    .and()
		    .exceptionHandling() // 인증 및 권한 부여와 관련된 예외 처리를 할 수 있다.(예외처리는 인증되지 않은 요청이나 인증된 사용자가 권한이 없는 리소스에 액세스하려고 할 떄 발생하는 상황에서 사용된다)
//		    인증에 실패한 요정의 응답을 원하는 대로 정할 수 있다. class로 만들어서 할수 있지만, 시큐리티가 기본적으로 제공하는 Http403ForbiddenEntryPoint 을 사용한다.
//		    Http403ForbiddenEntryPoint객체는 매서스가 명시적으로 반환하는 것은 없지만 response객체에 err매세지를 담는다. 그자체가 HTTP 통신을 할때 값을 전달할 수 있는듯 하다.
		    .authenticationEntryPoint(new Http403ForbiddenEntryPoint());
		    
		
		// filter 등록
		// 매 요청하다  CorsFilter 실행한 후에  jwtAuthenticationFilter 실행한다.
//		리다이랙트 되기전에 redirectUrlFilter를 실행한다.(쿠키생성해서 response에 저장하는것)
		return http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class)
				   .addFilterBefore(redirectUrlFilter, OAuth2AuthorizationRequestRedirectFilter.class).build();
	}

}
