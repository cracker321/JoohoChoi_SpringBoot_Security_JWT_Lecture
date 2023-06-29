package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


//--------------------------------------------------------------------------------------------


//============================================================================================


//'스프링 시큐리티 필터'를 '스프링 시큐리티 필터 체인'에 등록시키는 어노테이션.
//여기서 '스프링 시큐리티 필터'란 여기의 '클래스 SecurityConfig'를 말하는 것임.
@EnableWebSecurity
@Configuration //현재 클래스가, '구성 클래스'임을 나타내줌.
public class SecurityConfig extends WebSecurityConfigurerAdapter {





    //============================================================================================


    //< 스프링 시큐리티 내장 쿨래스 BCryptPasswordEncoder >

    //- 신규 회원가입 등에서 사용되는 '비밀번호'를 '암호화(인코딩)'하는 데 사용되는 내장 클래스.
    //  사용자가 노트북 화면에서 입력한 비밀번호를 암호화(인코딩), 해시, 검증하는 역할임.



    //--------------------------------------------------------------------------------------------

    /*

    < @Bean >

    - 이 어노테이션이 붙은 메소드의 리턴되는 객체를 IoC로 등록해주는 역할임

    # @Bean을 사용하여 객체를 만드는 순서
    순서 1)


    # 예시
    순서 1) '@Configuration'이 붙어 있는 클래스를 작성하고,
    순서 2) 그 내부에 '@Bean'이 붙어 있는 메소드를 작성한 뒤,
    순서 3) '@Bean'이 붙어 있는 메소드는, 해당 객체의 인스턴스를 생성하고 반환한다.
            그러면, 스프링 IoC 컨테이너는 @Bean이 적용된 메소드를 인식하고, 해당 빈 객체를 생성함.

    여기서 'SomeService 객체'는 '스프링 컨테이너에서 관리되는 빈 객체'임.

    @Configuration
    public class AppConfig{

        @Bean
        public SomeService someService(){

            return new SomeServiceImpl();
        }
    }

    순서 4) 'SomeService 객체'를 필요로 하는 곳에서는, 아래처럼 이를 주입받아 사용할 수 있음.

    @RestController
    public class SomeController{

        private final SomeSerivce someService;


        //생성자 주입 방식을 통한 의존성주입 DI
        public SomeController(SomeService someService){
            this.someService = someService;
        }
    }

    //--------------------------------------------------------------------------------------------


    < '스프링 JPA에서 생성자를 사용하여 객체를 만드는 방식'과 '스프링의 @Bean을 통한 객체 생성'의 공통점 및 차이점 >

    # 공통점
    - 객체 생성: 양쪽 방식 모두 객체 생성 목적으로 가짐
    - 의존성 관리: 두 방식 모두 객체 간의 의존성을 관리할 수 있음

    # 차이점
    - 사용 목적
      (1) @Bean
        : 스프링 컨테이너에서 관리하는 빈(Bean) 객체를 등록할 때 사용함.
          이렇게 등록된 빈 객체는 스프링 컨테이너에서 싱글톤(scope 기본값)으로 관리되어, 애플리케이션 전반에서
          재사용 가능한 객체를 생성할 때 유용함.
          주로, 서비스, 레퍼지터리, 설정 등의 같은 공통 컴퍼넌트를 정의할 때 사용함.
      (2) JPA의 엔티티 클래스의 생성자를 사용하여 객체를 만드는 것
        : JPA에서의 일반적인 객체 생성 방식.
          이 방식으로 생성된 객체는 '스프링 컨테이너에서 관리되지 않으며', 외부 클래스에서 해당 객체를 호출할 때마다
          새로운 인스턴스(객체)가 생성도미. 주로 도메인 객체나 DTO 등을 생성할 때 사용됨.
    - 라이프사이클 관리
      (1) @Bean
        : @Bean을 사용하여 생성된 객체는, 스프링 IoC 컨테이너에 의해 라이프사이클이 관리됨.
          스프링 컨테이너는 빈의 생성, 의존성 주입, 초기화, 소멸 등을 관리함.
      (2) JPA의 엔티티 클래스의 생성자를 사용하여 객체를 만드는 것
        : 엔티티 클래스의 생성자로 생성된 객체는 일반적인 자바 객체로 취급되며, 스프링 IoC컨테이너는
          엔티티 클래스로 생성된 자바 객체의 라이프사이클을 관리하지 않음.
          해당 객체의 생성 및 소멸은 사용자가 수동으로 처리해야 함.


    - 결론적으로,
     '@Bean 객체 생성'을 사용하는 것은, 스프링 컨테이너에서 관리되는 공용 객체를 생성할 때 사용하며,
     '엔티티 클래스의 생성자를 사용한 객체 생성'은, JPA에서 일반적인 객체 생성을 위해 사용됨.
      엔티티는 주로 DB와 매핑되는 객체로 사용되기 때문에, 생성자를 통해 객체를 생성할 때 적합하다고 할 수 있음.

     */

    @Bean
    public BCryptPasswordEncoder encodePwd(){

        return new BCryptPasswordEncoder();
    }




    //============================================================================================


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
                //    로그인 화면("/loginForm")으로 이동시켜주는 코드.
                .and()
                .formLogin()
                .loginPage("/loginForm");

    }

    //============================================================================================



}

