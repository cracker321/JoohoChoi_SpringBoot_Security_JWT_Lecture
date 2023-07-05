package com.cos.security1.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomBCryptPasswordEncoder extends BCryptPasswordEncoder {

    /*
    < '클래스 PrincipalOauth2User'와 '클래스 SecurityConfig
      (구체적으로는 '클래스 SecurityConfig의 메소드 public BCryptPasswordEncoder encoder')'간의 순환참조를 끊어내는 방법 >

    순서1) 아래처럼 여기 별도의 '클래스 BcryptPasswordEncoder'를 생성해주고, @Component를 붙여서 이것을 스프링 컨테이너에 등록시킨다.
    @Component
    public class CustomBCryptPasswordEncoder extends BCryptPasswordEncoder {

    }


    순서2) '클래스 SecurityConfig의 메소드 public BCryptPasswordEncoder encoder()'를 '주석 처리해준다'!

    ...

    @Bean()
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    ...

    //-------------------------------------------------------------------------------------------------

    순환 참조가 발생하는 이유:

    1. SpringContainer에서 처음 빈으로 등록하기 위해 객체를 생성 하여 줍니다(싱글톤)

    2. 그래서 SecurityConfig 객체를 생성하던 중
    PrincipalOauth2UserService객체를 의존하고있네요?
    그래서 PrincipalOauth2UserService를 만들어 주는데..

    3. 어라? PrincipalOauth2UserService에서도 SecurityConfig에서
    빈으로 등록한 BCryptPasswordEncoder를 참조하고있네??

    4. 오잉? 스프링:참조가 순환되넹?아아아아아악! => 오류

    즉,
    SecurityConfig -> PrincipalOauth2UserService,
    다시 PrincipalOauth2UserService->SecurityConfig

    그래서 저는 SecuritiConfig -> PrincipalOauth2UserService->
    CustomBCryptPasswordEncoder 로 구조를 변경하였습니다.
     */
}
