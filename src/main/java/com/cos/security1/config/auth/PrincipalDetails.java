package com.cos.security1.config.auth;






//--------------------------------------------------------------------------------------------


//============================================================================================


import com.cos.security1.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


//[ '스프링부트 시큐리티 4강 - 시큐리티 로그인'강 00:00~ ]


//< '스프링 시큐리티의 내장 인터페이스 UserDetails'를 구현하기 위한 '내 임의의 사용자 클래스 PrincipalDetails' >

//- 사용자의 '인증 정보'와 '권한 정보'를 제공하는 역할
//- 이 클래스는 '내장 인터페이스 UserDetails'를 구현하고, 인증된 사용자의 정보를 스프링 시큐리티에 전달하는 역할임.



@Data
//현재 클래스에 'final이 붙은 필드' 또는 '@NonNull이 붙은 필드'가 전혀 존재하지 않는다면,
//'@Data'는 자동으로 해당 클래스의 '기본 생성자'를 생성해줌.
//(근데 이거 확실한건가..? 아닌 것 같은데..)



//생성자는 클래스에서 뗄레야 뗄수 없는 관계이다.
//모든 클래스는 생성자를 갖고 있으며, 기본 생성자는 만들지 않아도 생성이된다.
//다만, 기본 생성자 이외의 생성자를 만들게 된다면 기본 생성자는 자동으로 만들어지지 않으므로,
//기본생성자 또한 만들어줘야 한다는 점 잊지 말도록하자



// 4. 생성자 특징
//         1) 생성자 이름 == 클래스 이름.
//         2) 메서드와 다르게 반환형이 없음.
//         3) 클래스는 반드시 한 개 이상의 생성자를 가지고 있음.
//         4) 생성자 오버로딩이 가능함(즉, 여러 개의 생성자가 존재 할 수 있음)
//         5) 생성자가 보이지 않으면 기본 생성자가 숨어 있음.
//         6) 기본생성자라 함은 매개변수가 없는 생성자를 말함.
//         7) 기본생성자 외에 다른 생성자를 만들면, 숨어 있던 기본생성자는 사라짐.
//         8) 기본 생성자 외에 다른 생성자를 만들면, 무조건 기본 생성자를 만들어 줄 것
//         (만약 기본생성자를 만들지 않으면 상속에서 문제 발생)



//Lombok을 사용했을 때, @Data를 붙이면 기본생성자로 @NoArgsConstructor를 생성해주는 줄 알았다.
//그런데 누가 물어봐서 대답하려고 보니, 명확하게 모르는 것을 깨닫고 찾아보았다.
//@Data를 붙이게 되면, 일반적으로는 @NoArgsConstructor를 만들어주는 것처럼 보인다.
//하지만 엄밀히 말하면 @NoArgsConstructor가 아니라 @RequiredArgsConstructor가 생성된다.
//즉, final접근자가 붙어있거나 @Nonnull 애노테이션을 붙인 경우에는 해당 필드를 가진 생성자가 만들어진다.
//보통의 경우는 final이나 @Nonnull이 없는 경우라서 @NoArgsConstructor를 만들어 주는 것 같은 착시효과를 보일 뿐이다.


//@RequiredArgsConstructor //- 이 @ReuqiredArgsConstructor는 오직 'final이 붙은 필드' 또는 '@NonNull이 붙은 필드'에
                           //  대해서만 생성자를 대신 만들어줌.
                           //  오직 'final이 붙은 필드' 또는 '@NonNull'이 붙은 필드에 대해서만 작용하고,
                           //  'final 또는 @NonNull이 붙은 필드가 없는 클래스'일 경우, 클래스에 어떠한 아무 영향도 미치지 못한다!
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user; //'일반 로그인'할 때 사용하는 의존성주입 및 생성자(@RequiredArgsConstructor)
    private Map<String, Object> attributes; //'OAuth2 로그인'할 때 사용하는 의존성주입 및 생성자(@AllArgsConstructor)



    public PrincipalDetails(User user){
        this.user = user;
    }



    public PrincipalDetails(User user, Map<String, Object> attributes){

        this.user = user;
        this.attributes = attributes;
    }

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

    //< '사용자 클래스 PrincipalDetails'가 '내장 클래스 OAuth2User'를 상속받기 위해서
    //   반드시 '오버라이딩'해야 하는 '메소드 getAttributes' >

    @Override
    public Map<String, Object> getAttributes() {
        return attributes; //이 attributes에 대해 확실히 알아보기.
    }

    //--------------------------------------------------------------------------------------------

    //< '사용자 클래스 PrincipalDetails'가 '내장 클래스 OAuth2User'를 상속받기 위해서
    //   반드시 '오버라이딩'해야 하는 '메소드 getName' >

    @Override
    public String getName() {
        return null;
    }


    //============================================================================================


    //[ '스프링부트 시큐리티 4강 - 시큐리티 로그인'강 00:00~ ]


    //< 스프링 시큐리티의 내장 인터페이스 UserDetails의 내장 메소드 getAuthorities() >


    //- '현재 사용자 User의 권한 정보'를 Collection 형태로 저장 후, 이를 리턴해줌.
    //- 여기서 '권한'은 스프링 시큐리티에서 '인터페이스 GrantedAuthority'로 표현됨.
    //- 아래 로직으로 구현된 '메소드 getAuthorities'는, 스프링 시큐리티를 사용할 때,
    //  인증된 사용자의 권한(Role)에 따라, 접근(access) 허용 여부를 결정하게 됨.



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        //순서 1) '모든 권한 GrantedAuthrority 객체'를 담을 컬렉션인 'ArrayList 객체'를 생성함(=초기화함).
        //       여기서 Collection<GrantedAuthority>는 그냥 'List<Member>'와 비슷한 개념임.
        //       List보다 상위 개념인 Collection을 사용한 것임.
        Collection<GrantedAuthority> collect = new ArrayList<>();


        //- '사용자 User의 역할(role)'을 가져와서 이것을 '권한(GrantedAuthority) 객체'로 생성하여,
        //  컬렉션에 추가하는 것임.
        //- '권한 GrantedAuthority'는 인터페이스이므로, '익명 내부 클래스'를 사용하여 '메소드 getAuthority'를 구현함.
        //- 순서 2) 'user.getRole()'을 호출하여, '현재 사용자의 역할'을 가져오고,
        //  순서 3) 그 해당 사용자의 역할을 가진 '권한 GrantedAuthority 객체'를 생성하고,
        //  순서 4) 이를 컬렉션에 추가함.
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });

        //순서 5) 이렇게 생성된 컬렉션은 '권한 GrantedAuthority 객체를 담은 컬렉션'이 되었고, 이를 반환함.
        return collect;
    }


    //============================================================================================


    //< '해당 사용자 User의 비밀번호'를 리턴해줌 >

    @Override
    public String getPassword() {

        return user.getPassword();
    }

    //============================================================================================


    //< '해당 사용자 User의 이름'를 리턴해줌 >

    @Override
    public String getUsername() {

        return user.getUsername();
    }


    //============================================================================================


    //< 네 계정이 만료됐니? >

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //============================================================================================


    //< 네 계정이 잠겼니? >

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //============================================================================================


    //< 네 계정 비밀번호 너무 오래된 거 아니니? 유효기간 지난 거 아니니? >
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    //============================================================================================


    //< 네 계정이 현재 활성화된 상태니? >

    @Override
    public boolean isEnabled() {


        //[ '스프링부트 시큐리티 4강 - 시큐리티 로그인'강 10:15~ ]

        //- 만약, 우리 사이트의 어떤 회원이 1년 동안 로그인을 안했다면, 그 회원 계정은 휴면 계정으로 전환시키고자 함.
        //  (근데, 이건 강의에서 아주 짧게 잠깐만 말하고 넘어갔음. 여기서는 일단 이 기능 적용X).

        return true;
    }




    //============================================================================================



}
