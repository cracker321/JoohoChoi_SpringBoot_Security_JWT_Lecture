package com.cos.security1.config.auth;






//--------------------------------------------------------------------------------------------


//============================================================================================


import com.cos.security1.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


//[ '스프링부트 시큐리티 4강 - 시큐리티 로그인'강 00:00~ ]


//< '스프링 시큐리티의 내장 인터페이스 UserDetails'를 구현하기 위한 '내 임의의 사용자 클래스 PrincipalDetails' >

//- 사용자의 '인증 정보'와 '권한 정보'를 제공하는 역할
//- 이 클래스는 '내장 인터페이스 UserDetails'를 구현하고, 인증된 사용자의 정보를 스프링 시큐리티에 전달하는 역할임.


@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails {

    private final User user; //



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
