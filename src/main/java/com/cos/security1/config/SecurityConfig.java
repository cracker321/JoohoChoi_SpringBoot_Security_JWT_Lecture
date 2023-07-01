package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


//--------------------------------------------------------------------------------------------


//============================================================================================


//- @EnableWebSecurity: '스프링 시큐리티 필터'를 '스프링 시큐리티 필터 체인'에 등록시키는 어노테이션.
//                      여기서 '스프링 시큐리티 필터'란 여기의 '클래스 SecurityConfig'를 말하는 것임.
@EnableWebSecurity

//< @EnableGlobalMethodSecurity >

//- '외부 클래스에서 호출하고자 하는 특정 컨트롤러 메소드 위에 붙어 있는 '@Secured(...)''와 함께 연동되어 사용되며,
//  외부 클래스에서 @Secured(...) 어노테이션이 붙어 있는 '메소드'를 호출하여 사용하려면(@GetMapping(...) 등 내부의 URL주소에 접근하려면),
//  '그 괄호 안에 있는 권한, 역할'을 가지고 있는 사용자여야 한
//- '클래스 단위 위에 붙는 @EnbableGlobalMethodSecurity(securedEnabled = true)'를 사용하면, 스프링은
//  '컨트롤러 메소드 단위 위에 붙는 @Secured(...)'를 인식하고, 웹 애플리케이션에서 특정한 권한 또는 역할을 가진 사용자만이
//  해당 메소드를 호출할 수 있도록 제한한다.
//- 여기서는 '컨트롤러 IndexController의 메소드 info'위에 '@Secured('ROLE_ADMIN')'이 붙어 있으며,
//  '이 메소드의 URL주소 @GetMapping("/info"), 즉 "/info"'에 접근하려면, '관리자 권한('ROLE_ADMIN')'이 있는 사용자여야
//  그 URL주소에 접근할 수 있다!
//- 'securedEnabled = true': '컨트롤러 메소드 info'의 위에 붙어 있는 '어노테이션 @Secured'을 사용하기 위해, 이를 활성화시킴.
//- 'prePostEnabled = true': '컨트롤러 메소드 info2'의 위에 붙어 있는 '어노테이션 @PreAuthorize'를 사용하기 위해, 이를 활성화시킴.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration //현재 클래스가, '구성 클래스'임을 나타내줌.
public class SecurityConfig extends WebSecurityConfigurerAdapter {


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

                .and()

                //(5)-1 사용자가 노트북 화면에서 스프링 시큐리티가 제공하는 '폼 form 기반 인증'을 사용할 수 있도록 설정해주는 메소드.
                .formLogin()

                //(5)-2 위 (1)~(3)의 URL주소로 '권한 없이 접근할 때', 그 권한 없이 접근한 사람에게
                //      로그인 화면("/loginForm")으로 이동시켜주는 코드.
                .loginPage("/loginForm")

                //(5)-3 사용자가 노트북 화면에 자신의 실제 로그인 정보(자신의 아이디, 그 아이디의 비밀번호)를 입력할 수 있도록
                //      해주는 '폼 form 형식'을 제공하는 메소드.
                //      순서 1) 사용자가 자신의 실제 로그인 정보를 다 입력하고 '제출' 버튼을 노트북 화면에서 누르면,
                //      순서 2) 사용자가 입력한 그 로그인 정보(=폼 데이터)를 URL주소 '/login'으로 POST 요청으로 전송됨(보내짐).
                //      순서 3) 스프링 시큐리티의 내부 메커니즘이 이 URL주소 '/login'를 처리하고,
                //             사용자 이름, 아이디, 비밀번호 등의 정보를 검증함.
                //      순서 4) 사용자 인증이 성공하면, 사용자는 원래 접속(요청)하려던 페이지로 접속할 수 있게 되고,
                //             사용자 인증이 실패하면, 사용자는 로그인 페이지로 다시 리다이렉트되고, 인증 실패 메시지가 표시될 수도 있음.
                .loginProcessingUrl("/login")

                //(5)-4 사용자 인증이 성공했다면(로그인이 성공했다면), 그 사용자가 최초에 원래 접속하려던 페이지의 URL이 아닌,
                //      루트 경로인 '/'로 그 사용자를 보내줌(리다이렉트해줌)
                .defaultSuccessUrl("/");

    }

    //============================================================================================


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


    //============================================================================================



    //< 스프링 시큐리티 내장 쿨래스 BCryptPasswordEncoder >

    //- 신규 회원가입 등에서 사용되는 '비밀번호'를 '암호화(인코딩)'하는 데 사용되는 내장 클래스.
    //  사용자가 노트북 화면에서 입력한 비밀번호를 암호화(인코딩), 해시, 검증하는 역할임.
    //순서 1) BCryptPasswordEncoder 클래스 인스턴스 생성
    //      - BCryptPasswordEncoder는 스프링 시큐리티에서 제공하는 비밀번호 암호화(인코딩) 내장 클래스임.
    //        사용자 비밀번호를 암호화, 해시하고 검증함. 여기서는 그 클래스의 인스턴스를 생성함.
    //순서 2) 비밀번호 암호화(인코딩) 방식설정
    //      - BCryptPasswordEncoder는 동일한 비밀번호더라도, 매번 다른 해시 값을 생성하고 사용한다.
    //        이를 위해 BCrypt 해시 알고리즘을 사용하여 솔트값을 생성함.
    //순서 3) 객체 반환
    //      - 생성된 BCryptPasswordEncoder 객체를 반환하고, 이후 컴포넌트 내(의 다른 외부 클래스)에서
    //        이 객체를 주입받아 사용할 수 있음.

    @Bean
    public BCryptPasswordEncoder encodePwd(){

        return new BCryptPasswordEncoder();
    }



    //============================================================================================


}

