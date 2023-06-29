package com.cos.security1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity //'스프링 시큐리티 필터'를 '스프링 시큐리티 필터 체인'에 등록시키는 어노테이션.
//여기서 '스프링 시큐리티 필터'란 여기의 '클래스 SecurityConfig'를 말하는 것임.
@Configuration //현재 클래스가, '구성 클래스'임을 나타내줌.
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    //[ '스프링부트 시큐리티 2강 - 시큐리티 설정'강 00:00~ ]


    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http.csrf().disable(); //csrf 설정을 비활성화함.
        http.authorizeRequests()
                //(1) URL주소 '/user/**'로 들어오는 입력은, '로그인 절차(=인증 절차)를 거치는 것이 필수'.
                .antMatchers("/user/**").authenticated()

                //(2) URL주소 '/manager/**'로 들어오는 입력은, 'ROLE_ADMIN'권한 또는 'ROLE_MANAGER' 권한이 있는
                //    사람만 접근 가능하도록 설정함.
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")

                //(3) URL주소 '/manager/**'로 들어오는 입력은, 'ROLE_ADMIN'권한이 있는 사람만 접근 가능하도록 설정함.
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")

                //(4) 위 (1)~(3)의 URL 주소가 아닌 다른 URL주소로 들어오는 입력은, 로그인 제한이나 권한 제한
                //    필요 없이 그냥 자유롭게 해당 사이트에 접근 가능함. 권한 허용('permitAll')
                .anyRequest().permitAll()

                //(5) 위 (1)~(3)의 URL주소로 '권한 없이 접근할 때', 그 권한 없이 접근한 사람에게
                //    로그인 화면("/logiin")으로 이동시켜주는 코드.
                .and()
                .formLogin()
                .loginPage("/login");

    }

}

