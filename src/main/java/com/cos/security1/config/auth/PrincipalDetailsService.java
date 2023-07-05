package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


//--------------------------------------------------------------------------------------------


//< 스프링 시큐리티의 내장 인터페이스 UserDetailsService >

//- 스프링 시큐리티에서 제공하는 내장 인터페이스로,
//  '외부 클래스에서 내장 메소드 loadUserByUsername을 호출할 때, 그 때 파라미터로 입력된 사용자 정보를 DB로부터 가져오는 역할'을 함.
//- 이 내장 인터페이스를 사욯하기 위해서는, 그 구현하는 클래스(여기서는 '클래스 PrincipalDetailsService') 내부에서,
//  '이 내장 인터페이스의 내장 메소드 loadUserByUsername'을 반드시 그 오버라이딩해 가져와서 재정의해줘야 함!!

//--------------------------------------------------------------------------------------------


//< 스프링 시큐리티의 내장 인터페이스 UserDetailsService를 구현하기 위한 내 임의의 사용자 클래스 서비스 PrincipalDetailsService >

//- 'PrincipalDetails 객체'는 '내장 인터페이스 UserDetails를 구현한 커스텀 클래스'로,
//  '사용자의 식별 정보', '비밀번호', '권한' 등을 담고 있음.
//- 사용자 인증 과정(로그인 과정)에서 인증된 사용자의 정보를 나타내는 객체로 사용됨.
//  스프링 시큐리티는 이 객체를 통해 사용자를 인증하고 권한을 부여함.


//--------------------------------------------------------------------------------------------


//'@Service 어노테이션'을 통해, 이 '클래스 서비스 PrincipalDetailsService'를 '스프링 빈 객체'로 등록함.
//이제, 스프링이 이 서비스 클래스의 인스턴스를 관리하고, 필요한 곳에 주입하게 됨.
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    //============================================================================================


    //[ '스프링부트 시큐리티 4강 - 시큐리티 로그인'강 12:10~ ]


    //< 스프링 시큐리티의 내장 인터페이스 UserDetailsService의 내장 메소드 loadUserByUsername >

    //- '외부 클래스에서 내장 메소드 loadUserByUsername을 호출할 때, 그 때 파라미터로 입력된 사용자 정보를 DB로부터 가져오는 역할'을 함.
    //- '스프링 시큐리티 내장 인터페이스 UserDetailService'를 구현하려면, 반드시 오버라이딩해야 하는 그 내장 메소드임.
    //- 스프링 시큐리티는 이 내장 메소드 loadUserByUsername을 통해 사용자 인증 정보를 얻음.
    //  '사용자 이름 username'을 기반으로 사용자 정보를 가져오는 역할을 수행함.
    //- 스프링시큐리티의 인증 프로세스 과정에서 이 메소드 loadUserByUsrename이 외부 클래스에서 호출되어 이 메소드가 사용될 때,
    //  이 메소드는 '전달받은 사용자 이름 username'을 사용하여, '실제 사용자 정보를 조회'한 뒤 'UserDetails 객체를 반환'한다!
    //- 이 내장 메소드는 '사용자 이름 username'을 '파라미터'로 받음.
    //  스프링 시큐리티는 로그인 과정에서 사용자가 입력한 username을 전달함.


    //============================================================================================

    /*

    < 스프링 시큐리티 '세션' >

    - 사용자의 인증 정보를 세션에 저장하여 관리함.
    - 세션에 저장되는 정보는 'Authentication 객체' 딱 하나임.
    - 사용자가 로그인하면, 스프링 시큐리티는 Authentication 객체를 생성하고, 인증된 사용자의 정보를 담아 세션에 저장함.
                 ㅣ
                 ㅣ
                 ㅣ 하위 개념으로 내려가는 것.
                 ㅣ
                 ㅣ
    < 내장 Authentication 객체 >

    - 스프링 시큐리티에서 '사용자의 신원을 확인하는 과정'에서 사용되며, '로그인(인증)에 성공한 사용자'를 나타내는 객체임.
      사용자가 누구인지 확인하고, 애플리케이션에 대한 접근 '권한'이 있는지 확인하는 절차임.
      일반적으로, 사용자는 ID, 비밀번호, 이메일 또는 소셜 계정 등을 통해 인증됨.
    - '내장 Authentication 객체'는, '내장 Principal 객체'와 '내장 Credentials 객체' 두 가지 정보를 포함하여 구성됨.
    - '내장 Principal 객체'
       :'인증된 사용자(=사용자의 인증 정보)를 나타내는 객체'로,
        주로 사용자의 신원정보(=식별자)(이름 username, 이메일 email 등)를 포함하고 있음.
    - '내장 Credentials 객체'
       : '사용자가 제공한 자격 증명(비밀번호, 토큰 등)'을 나타내는 객체임.
                 ㅣ
                 ㅣ
                 ㅣ 하위 개념으로 내려가는 것.
                 ㅣ
                 ㅣ
    < 내장 Authentication 객체의 두 개의 제한된 필드: '내장 인터페이스 UserDetails 타입'과 '내장 인터페이스 OAuth2User 타입' >

    - 내장 Authentication 객체에는 특정한 필드만 저장할 수 있음.
      따라서, 원칙적으로는 내장 Authentification 객체는
      (1)'일반 로그인 스프링 시큐리티 인증 정보' 또는 (2)'소셜 로그인 OAuth2 인증 정보'
      중 오직 '단 한 가지만 저장'할 수 있음.
    - 1. '내장 인터페이스 UserDetails'
         : '일반 로그인'할 때 사용되는 스프링 시큐리티에서 제공하는 내장 인터페이스.
           '사용자 정보'와 '권한 정보'를 담고 있음
    - 2. '내장 인터페이스 OAuth2User'
         : 'OAuth2 인증 로그인(=소셜 로그인)' 방식을 사용할 때 사용되는 내장 인터페이스로, '사용자 '속성' 정보'를 담고 있음.
                 ㅣ
                 ㅣ
                 ㅣ 하위 개념으로 내려가는 것.
                 ㅣ
                 ㅣ
    < '내장 인터페이스 UserDetails'와 '내장 인터페이스 OAuth2User'에 '사용자의 세부 정보'를 전달해주기 위해,
     각각 'DB'와 'OAuth 2.0 제공자(=구글)'로부터 그 '사용자의 세부 정보'를 '조회해서 가져오는 역할'을 하는
      '내장 인터페이스 서비스 UserDetailsService'와 '내장 인터페이스 서비스 DeafaultOAuth2User' >

    1-(1). '내장 인터페이스 UserDetails'
           - '사용자의 세부 정보를 담고 있는 클래스 PrincipalDetails'가 구현하고 있는 내장 인터페이스로,
             사용자의 계정 정보와 권한 정보를 담고 있는 인터페이스임.
             인증 과정에서 '이 내장 인터페이스를 구현하는 클래스 PrincipalDetails'를 사용하여 사용자의 세부 정보를 제공함.
           - 주요 내장 메소드:
             getUsername(): 사용자의 이름을 반환함.
             getPassowrd(): 사용자의 비밀번호를 반환함.
             getAuthorities(): 사용자가 가지고 있는 권한 목록을 반환함.
             isAccountNonExpired(): 사용자의 계정이 만료되었는지 여부를 반환함.
             isAccountNonLocked(): 사용자의 계정이 잠겨있는지 여부를 반홤함.
             isCredentialsNonExpired(): 사용자의 인증 정보가 만료되었는지 여부를 반환함.
             isEnabled(): 사용자의 계정이 활성화되었는지 여부를 반환함.

    1-(2). '내장 인터페이스 서비스 UserDetailsService'
           - 'DB로부터' '사용자의 세부 정보'를 '조회해서 가져오는 역할'을 담당함.
           - 주요 내장 메소드:
             loadUserByUsername(String username) : 주어진 사용자 이름에 해당하는 사용자 정보를 'DB로부터' 조회하여
                                                   'UserDetails 객체를 반환'한다!



    2-(1). '내장 인터페이스 OAuth2User'
           - '사용자의 세부 정보를 담고 있는 클래스 PrincipalDetails'가 구현하고 있는 내장 인터페이스로,
             OAuth 2.0 기반의 인증을 통해 얻은 사용자의 '속성(attributes)' 정보를 담고 있는 인터페이스임.
             인증 과정에서 '이 내장 인터페이스를 구현하는 클래스 PrincipalDetails'를 사용하여 사용자의 속성 정보를 제공함.
           - 주요 내장 메소드:
             getName(): OAuth 2.0으로 인증된 사용자의 이름을 반환함. 사용자를 식별하는 데에 사용될 수 잇음.
             getAttributes(): 인증된 '사용자의 속성(attribute) 정보'를 반환함.
                              이는 보통 사용자의 프로필 정보와 같은 추가 정보를 제공하는 데 활용됨.
             getAuthorities(): 인증된 '사용자의 권한 정보'를 반환함.
                               이는 보통 권한 부여 및 인가 작업에 사용됨.


    2-(2). '내장 인터페이스 서비스 DefaultOAuth2UserService'
           - 'OAuth2 제공자(=구글 등)로부터' '사용자의 세부 정보'를 '조회해서 가져오는 역할'을 담당함.
           - 주요 내장 메소드:
             loadUser(OAuth2UserRequest userRequest): 주어진 사용자 이름에 해당하는 사용자 정보를
                                                     'OAuth 2.0 제공자(=구글 등)로부터' 조회하여 'OAuth2User 객체를 반환'함!
                 ㅣ
                 ㅣ
                 ㅣ 하위 개념으로 내려가는 것.
                 ㅣ
                 ㅣ
    < '사용자의 세부 정보'를 담고 있는 '내장 인터페이스 UserDetails'와 '내장 인터페이스 OAuth2User'를
      구현하기 위해 만든 '사용자 정의 클래스 PrincipalDetails' >

    - '개발자가 필요로 하는 사용자 User의 상세 정보'를 담을 수 있도록 만든 클래스이며, 'User 객체'를 '포함'하여 사용자 정보를 저장함.
    - '내장 인터페이스 UserDetails' 또는 '내장 인터페이스 OAuth2SUer'를 '커스터마이징'하여 구현(implements)'하기 때문에,
      이 내장 인터페이스들 내부의 '사용자 정보와 권한을 제공하는 내장 메소드들'을 '오버라이딩'하여 '정의'해야 함(인터페이스이기 때문).
      또한, '커스터마이징한 클래스'이기 때문에, '사용자 User 정보'에 추가적인 필드들을 포함시켜도 됨.
    - 사용자 정보를 활용한 권한 검사, 추가 필드 활용, 특정 조건에 따른 동작 변경 등의 기능을 구현할 때 호라용됨.
    - '내장 객체 Authentication'은, 인증된 사용자를 식별하기 위해 'PrincipalDetails 객체'를 '스프링 시큐리티 세션에 저장'하고,
      이렇게 '세션에 저장된 Authentication 객체'는 후속 요청에서 사용자를 식별하고, 인증된 사용자의 정보에 접근하는 데 사용됨.

     */

    //============================================================================================


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //순서 1) '해당 사용자 User 객체'의 정보를 db로부터 조회해서 가져옴.
        User userEntity = userRepository.findByUsername(username);

        //순서 2)-1 사용자 정보 확인: db에 해당 사용자가 있어서, 그 사용자(사용자 정보)를 db로부터 가져온 경우
        //- db로부터 조회해서 가져온 사용자 User 객체의 정보가 null이 아닌 경우, 'PrincipalDetails 객체'를 '생성'하여 '반환'함.
        //- 'PrincipalDetails 객체'는, '사용자 인증 과정(로그인 과정)'에서 인증된 사용자의 정보를 나타내는 객체로 사용됨.
        //  스프링 시큐리티는 이 객체를 통해 사용자를 인증하고 권한을 부여함.
        if(userEntity != null){

            return new PrincipalDetails(userEntity);
        }

        //순서 2)-2 사용자 정보 확인: db에 해당 사용자가 없어서, 그 사용자(사용자 정보)를 db로부터 가져올 것이 없는 경우
        //- db에서 해당 사용자(사용자 정보)를 찾지 못한 경우, null을 반환함.
        else
            return null;

    }

    //============================================================================================


    //============================================================================================



}
