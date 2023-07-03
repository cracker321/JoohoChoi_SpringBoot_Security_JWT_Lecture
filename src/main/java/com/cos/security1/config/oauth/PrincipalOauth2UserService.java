package com.cos.security1.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


//[ '스프링부트 시큐리티 7강 - 구글 회원 프로필 정보 받아보기'강 05:00~ ]

//  < 클래스 PrinciplOauth2UserService 객체 >

//- '스프링 시큐리티 내장 클래스 DefaultOAuth2UserService'를 '상속 extends'한 임의의 사용자 정의 서비스.
//  'PrincipalOauth2UserService 객체'는 해당 사용자에 대한 OAuth2 인증(=구글 로그인 인증)을 하고,
//  이 객체 내부에서 그 인증을 완료하면, 그 인증 완료된 사용자 정보(=내장 OAuth2User 객체)를 포함(반환)하고 있음
//- 이 클래스 내부의 '오버라이딩된 메소드 loadUser'에서, 그 활용 처리 방법을 정의함.
//  '오버라이딩된 메소드 loadUser'는, 'OAuth



@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {



    //============================================================================================

    //[ '스프링부트 시큐리티 7강 - 구글 회원 프로필 정보 받아보기'강 06:40~ ]


    //< 구글 로그인 인증(=OAuth2 인증)이 완료된 뒤, 그 후 절차임 >
    // &
    //< 스프링 시큐리티 내장 클래스 DefaultOAuth2UserService의 내장 메소드 loadUser >


    // 순서1) '내장 메소드 loadUser'
    // - '스프링 시큐리티 내장 클래스 DefaultOAuth2UserService의 오버라이딩 내장 메소드 loadUser'는,
    //   구글 로그인이 완료된 이후에 호출되며,
    //   구글 로그인 후 구글API로부터 전달받은(반환된) 'OAuth2UserRequest 객체'를 인자값으로 하여
    //   이 'OAuth2UserRequest 객체'를 매개변수로 받음.
    //   이 'OAuth2UserRequest 객체'는, OAuth2 인증을 위해 필요한 정보를 포함하고 있음.


    // 순서2) 'OAuth2UserRequest 객체'
    // - 스프링 시큐리티의 OAuth2 로그인(=구글 로그인)을 수행한 뒤 호출되는 메소드로, 구글 로그인이 완료되면,
    //   개발자는 구글API로부터 'OAuth2UserRequest 객체'를 반환받는데,
    //   이를 인자값으로 받아 사용하여, 구글 API로부터 사용자 프로필 정보를 가져옴.
    // - 'OAuth2UserRequest 객체'는, '로그인 인증 완료된 사용자의 프로필 정보'와 '엑세스 토큰'을 요청하는 데 필요한 정보인
    //   'ClientRegistration 객체'와 'AuthorizedClient 객체'를 담고 있음.


    // 순서2)-1 'OAuth2UserRequest 객체'로부터 'ClientRegistration 객체'를 가져온다.
    // - 'ClientRegistration 객체'는 구글(=OAuth2 공급자)과 사용자(e.g: yujogn4170@gamil.com) 간의 등록 정보를 나타냄.


    // 순서2)-2 'ClientRegistration 객체'를 사용하여, 구글(=OAuth2 공급자)에게 '엑세스 토큰 AccessToken'을 요청하여 발급받아온다.
    // - '엑세스 토큰'은 구글로부터 발급되는 인증 토큰임. 이 토큰은, (로그인)인증된 사용자를 식별하고 그 사용자에게 권한을 부여하는 데
    //   사용됨.


    // 순서2)-3 '순서 2)-2'에서 구글로부터 발급받은 '엑세스 토큰'을 사용하여, 이제 구글에게 그 (로그인)인증된 사용자 정보를 요청한다.
    // - 이를 통해, 구글로부터 (로그인)인증된 사용자의 프로필 정보를 가져옴. 바로 여기 단계에서 구글과의 통신이 이루어짐.


    // 순서3) 구글로부터 받아온 '사용자 프로필 정보'를 기반으로 '내장 OAuth2User 객체'를 생성함.
    // - 이 '(로그인)인증된 사용자 정보를 담고 있는 내장 OAuth2User 객체'는 '인터페이스'임.
    //   사용자의 식별자(id값), 이름, 이메일 등의 속성을 포함하고 있음.


    // 순서4) '생성된 사용자 프로필 정보 OAuth2User 객체를 반환'함: return super.loadUser(userRequest)
    // - '내장 클래스 DefaultOAuth2UserService'의 '내장 메소드 loadUser'를 호출하여, 기본 구현을 활용함.
    //   '기본 구현'의 역할은 '구글로부터 사용자 프로필 정보를 가져오고', 'OAuth2User 객체를 생성'하여 '반환 return'해주는 것임.


    //--------------------------------------------------------------------------------------------

    //[ '스프링부트 시큐리티 8강 - Authentication객체가 가질 수 있는 2가지 타입'강 00:00s~ ]


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{

        return super.loadUser(userRequest);
    }



    //============================================================================================


}
