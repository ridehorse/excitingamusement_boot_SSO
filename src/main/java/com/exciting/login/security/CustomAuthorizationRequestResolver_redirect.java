package com.exciting.login.security;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import javax.servlet.http.HttpServletRequest;

public class CustomAuthorizationRequestResolver_redirect implements OAuth2AuthorizationRequestResolver {

    private final OAuth2AuthorizationRequestResolver defaultResolver;
    private final String githubRedirectUri;
    private final String kakaoRedirectUri;

/* DefaultAuthorizationRequestResolver는 OAuth 2.0 인증 요청의 기본적인 해결 로직을 구현한 클래스입니다. 
 * 주로 OAuth 2.0 로그인을 처리할 때, 클라이언트(사용자 애플리케이션)가 제공한 인증 요청을 해석하고, 제공자(인증 서버)로 전달하기 전에 필요한 정보를 채워넣습니다.
이 클래스를 생성할 때, 생성자에 전달되는 파라미터들은 다음과 같습니다:
ClientRegistrationRepository: 클라이언트 등록 정보를 관리하는 데 사용되는 리포지토리입니다.
authorizationRequestBaseUri: 클라이언트가 사용자를 인증하기 위해 제공자에게 전송하는 인증 요청의 기본 URI입니다.
(클라리언트에서 백엔드로 주소를 보내면 이 주소가 "/login/authGit/{registrationId}" 와 일치하면 github 인증서버로 사용자의 정보를 보낸다)
위에서 제공되는 "/login/authGit/{registrationId}"는 인증 요청을 받을 endpoint의 패턴을 나타냅니다. {registrationId} 부분은 클라이언트 등록 ID에 해당하는 동적인 값으로 대체됩니다. 
이는 여러 클라이언트에서 동일한 endpoint를 사용할 수 있도록 하기 위한 것입니다.
CustomAuthorizationRequestResolver 클래스는 이 DefaultAuthorizationRequestResolver를 래핑하고, 필요에 따라 리다이렉트 URI를 사용자 정의하고 있습니다. 
이를 통해 특정 클라이언트에 대해 리다이렉트 URI를 변경하거나 추가적인 로직을 수행할 수 있습니다. */
    public CustomAuthorizationRequestResolver_redirect(ClientRegistrationRepository repo, String githubRedirectUri, String kakaoRedirectUri) {
        this.defaultResolver = new DefaultOAuth2AuthorizationRequestResolver(repo, "/auth/authorize/{registrationId}");
        this.githubRedirectUri = githubRedirectUri;
        this.kakaoRedirectUri = kakaoRedirectUri;
    }
		
    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        return defaultResolver.resolve(request);
    }
		
/* clientRegistrationId 값은 어떻게 대입되는가?
clientRegistrationId 값은 사용자가 OAuth 2.0 클라이언트 등록 정보를 구성할 때 지정한 클라이언트 등록 ID입니다. 주로 서비스 제공자(예: GitHub, Naver)와의 연동을 위해 등록한 클라이언트 정보에 대한 식별자로 사용됩니다.
스프링 시큐리티 OAuth 2.0에서는 ClientRegistrationRepository를 통해 클라이언트 등록 정보를 관리합니다. 이 repository에 등록된 각 클라이언트의 등록 ID가 clientRegistrationId로 전달됩니다.
따라서 사용자가 GitHub 또는 Naver와 같은 OAuth 2.0 제공자와 연동하기 위해 등록한 클라이언트 정보에 따라 clientRegistrationId가 설정됩니다. 
스프링 시큐리티는 이 정보를 기반으로 해당 클라이언트의 OAuth 2.0 설정 및 동작을 처리합니다. */
/* */
    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        OAuth2AuthorizationRequest authorizationRequest = defaultResolver.resolve(request, clientRegistrationId);

        if (authorizationRequest != null) {
            // Customize the redirect URI based on the client registration ID
            String redirectUri;
            if ("github".equals(clientRegistrationId)) {
                redirectUri = githubRedirectUri;
            } else if ("kakao".equals(clientRegistrationId)) {
                redirectUri = kakaoRedirectUri;
            } else {
                // Handle other cases if needed
                redirectUri = "/default-redirect";
            }
						
/* OAuth2AuthorizationRequest.from(authorizationRequest)는 주어진 authorizationRequest를 기반으로 새로운 OAuth2AuthorizationRequest를 
생성하는 정적 팩토리 메서드입니다. 이 메서드는 빌더 디자인 패턴을 사용하여 새로운 객체를 생성하고 설정하는 데 사용됩니다.
여기서 각각의 메서드 호출은 빌더 패턴의 일부로서 특정한 속성을 설정합니다. 따라서 redirectUri(redirectUri)는 OAuth2AuthorizationRequest의 리디렉션 URI를 설정하고, 
build()는 최종적으로 설정된 속성들을 기반으로 OAuth2AuthorizationRequest 객체를 생성합니다.
이러한 빌더 패턴은 객체를 생성하고 초기화하는 데 유연성을 제공하며, 코드의 가독성을 향상시키는 데 도움이 됩니다. */
            // 그러니까 resolve method는 github, kakao에 따라 다른 주소들을 가진 OAuth2AuthorizationRequest 객제들을 만드는건데,
            // 그럼 이 객체를 사용하게 하려면 어떻게 해야하는거지?
						// OAuth2AuthorizationRequest authorizationRequest
            return OAuth2AuthorizationRequest.from(authorizationRequest)
                    .redirectUri(redirectUri)
                    .build();
        }

        return null;
    }
}
