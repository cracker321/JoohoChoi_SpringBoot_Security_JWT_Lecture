package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
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
