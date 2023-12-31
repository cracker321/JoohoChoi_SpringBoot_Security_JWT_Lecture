package com.cos.security1.controller;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller //'뷰'를 리턴
public class IndexController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //---------------------------------------------------------------------------------------

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
         : '일반 로그인'할 때 사용되는 스프링 시큐리티에서 제공하는 내장 인터페이스. '사용자 정보'와 '권한 정보'를 담고 있음
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
    


    //[ '스프링부트 시큐리티 8강 - Authentication객체가 가질 수 있는 2가지 타입'강 00:00~ ]

    //'Authentication 객체'가 가질 수 있는 두 가지 타입은,
    //- 1.UserDetails 객체 타입: '일반 로그인'할 때 사용됨.
    //- 2.OAuth2User 객체 타입: '소셜 로그인(OAuth2 로그인)'할 때 사용됨.


    //< 내장 Authentication 객체 >

    //- 스프링 시큐리티에서 '사용자의 신원을 확인하는 과정'에서 사용되며, '로그인(인증)에 성공한 사용자'를 나타내는 객체임.
    //  사용자가 누구인지 확인하고, 애플리케이션에 대한 접근 '권한'이 있는지 확인하는 절차임.
    //  일반적으로, 사용자는 ID, 비밀번호, 이메일 또는 소셜 계정 등을 통해 인증됨.
    //- '내장 Authentication 객체'는, '내장 Principal 객체'와 '내장 Credentials 객체'를 포함하여 구성됨.
    //- '내장 Principal 객체'
    //   :'인증된 사용자(=사용자의 인증 정보)를 나타내는 객체'로,
    //    주로 사용자의 신원정보(=식별자)(이름 username, 이메일 email 등)를 포함하고 있음.
    //- '내장 Credentials 객체'
    //   : '사용자가 제공한 자격 증명(비밀번호, 토큰 등)'을 나타내는 객체임.

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOAuthLogin(Authentication authentication,
                                               @AuthenticationPrincipal OAuth2User oauth){
        //- '내장 클래스 Authentication 객체'를 의존성 주입 DI해줌.

        System.out.println("/test/login ===============");

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        System.out.println("authentication" + oauth2User.getAttributes());

        System.out.println("oauth2User: " + oauth.getAttributes());

        return "OAuth2 세션 정보 확인하기";

    }
    
    
    //============================================================================================

    @GetMapping({"", "/"})
    public String index(){


        return "index";
        //웹브라우저 URL주소에 'localhost:8080'를 입력하면,
        //이제 화면에 'index.html' 속의 코드 구현 내용들이 보이게 됨.
    }


    //============================================================================================

    //[ '스프링부트 시큐리티 2강 - 시큐리티 설정'강 00:00~ ]

    //[ '스프링부트 시큐리티 9강 - 구글 로그인 및 자동 회원가입 진행 완료'강 00:00~ ]

    //- 'PrincipalDetails'를 매개변수로 사용하여, 이제
    //  사용자가 '일반 로그인'을 하든, 'OAuth2 로그인(소셜 로그인)'을 하든 상관 없이
    //  어떤 로그인이든 URL주소 '/user'에 접근이 가능해졌다!
    //- '@AutenticationPrincipal'
    //  : 이 어노테이션으로 접근하면, '일반 로그인'을 해도 PrincipalDetails 객체 반환되고,
    //    'OAuth2 로그인'을 해도 PrincipalDetails가 반환되어 어느 경우에도 자유롭게
    //
    //
    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails){



        return "user";
        //웹브라우저 URL주소에 'localhost:8080/user'를 입력하면,
        //이제 화면에 'user'라는 글자가 보이게 됨.
    }


    //< '@ReponseBody' >

    //- '@RepsnseBody'가 붙어 있는 현재 컨트롤러 메소드의 '리턴값'은 HTTP 응답 본문에 그대로 포함됨.
    //  즉, 별도의 '뷰 View'를 생성하거나 관리하지 않아도 되고, 그 반환값이 자바 객체라면 JSON 형식으로 직렬화
    //  변환되어, 반환값이 그 자체로 HTTP 응답 본문으로 사용되어 클라이언트에게 전달됨.
    //- 여기서는 '회원가입 완료됨!'문자열이 JSON 형식으로 변환되어 HTTP 응답 본문에 포함되어 클라이언트에게 전송됨.
    //- 이를 통해 개발자는 별도의 뷰 html 페이지를 생성하거나 관리하지 않고도 데이터를 손쉽게 HTTP 응답으로
    //  사용자에게 넘길 수 있음.



    //============================================================================================


    @GetMapping("/admin")
    public @ResponseBody String admin(){

        return "admin";
    }

    //============================================================================================


    @GetMapping("/manager")
    public @ResponseBody String manager(){

        return "manager";
    }


    //============================================================================================


    //[ '스프링부트 시큐리티 3강 - 시큐리티 회원가입'강 00:00~ ]


    //- 원래, '클래스 SecurityConfig'를 작성하기 전에는, URL주소에 'localhost8080:/login'을 입력했을 때,
    //  스프링시큐리티 최초 로그인해야 하는 푸른색 창이 떴는데,
    //  이후, '클래스 SecurityConfig'를 작성한 후에는, 이제 그런 푸른색 창이 뜨지 않고,
    //  정상적으로 화면에 'loginForm'문자열을 보여줌.
    @GetMapping("/loginForm")
    public String loginForm(){

        return "loginForm";
    }


    //============================================================================================


    @GetMapping("/joinForm")
    public String join() {
        return "joinForm";
    }


    //============================================================================================



    @GetMapping("/login")
    public String login() {
        return "login";
    }



    //============================================================================================


    //순서 1) '화면단'에서 '뷰 joinForm.html'에서 사용자(신규회원)가 신규회원가입 '폼 form'을 작성한 후,
    //순서 2) '서버단'인 여기로 그 정보가 'action 태그'에 적힌 'action="/join"'을 통해
    //        이 '컨트롤러 메소드 join'으로 넘어온다!


    //[ '스프링부트 시큐리티 3강 - 시큐리티 회원가입'강 14:00~ ]


    //< 신규 회원가입 >

    @PostMapping("/join")
    public String join(User user){

        //순서 1) '뷰 joinForm.html 내부의 폼 form 객체'를 통해 새로운 신규 '사용자 User 객체'가
        //       본인의 노트북 화면에 떠 있는 '신규회원가입에 필요한 본인 정보 (아이디, 비밀번호, 이메일)'을 입력한 후,
        //       그 노트북 화면에서 '제출, 확인 등의 버튼'을 누르면,
        //순서 2) '뷰 joinForm.html 내부의 action 태그 정보(action="/join")'에 '/join'으로 되어있는 로직에 따라,
        //       그 신규 사용자가 입력한 데이터(아이디, 비밀번호, 이메일)가 URL 주소 'localhost:8080/join'으로 넘어가게 되고,
        //순서 3) 유기적으로 연결되어 있는 서버단인 여기 @PostMapping("/join")으로 그 데이터가 넘어와서,
        //       신규 '사용자 User 객체'가 폼 form 객체에 입력한 정보(아이디, 비밀번호, 이메일) 이외에 더 존재해야 하는
        //       '사용자 User 객체의 필드(속성)'인 '필드 Role'의 속성값을 바로 여기 '컨트롤러 메소드 join' 에서
        //       'user.setRole("ROLE_USER")'라고 작성해주어 추가해주고,
        //       cf) 'createDate'는 자동으로 입력되기 떄문에, 따로 여기서 setCreateDate 이렇게 할 필요 없음.
        //순서 4) '레펏 UserRepository'를 통해 여기서 그 '사용자 User 객체 정보들'을 DB에 저장시키는 것이 순서가 된다!


        //DB에, '테이블 User의 컬럼 Role'의 '행(데이터) 값'으로 'ROLE_USER'가 입력되게 되는 것임.
        user.setRole("ROLE_USER");


        //userRepository.save(user);
        //- 만약, 이 단계에서 db로 저장시키면, 회원가입 자체는 잘 되지만,
        //  비밀번호가 암호화(인코디드)가 안 되어있는 상태이기 때문에, 시큐리티로 로그인 불가함.


        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);

        //회원가입 완료.
        //패스워드 암호화(인코딩)까지 완료해서 최종적으로 회원가입 완료.
        userRepository.save(user);


        //회원가입 로직을 완료한 후, 이제 '뷰 loginForm'으로 리다이렉션하여 거기로 보내줌.
        return "redirect:/loginForm";
    }


    //============================================================================================


    //[ '스프링부트 시큐리티 5강 - 시큐리티 권한처리'강 05:15~ ]

    /*
    - '클래스 SecurityConfig' 내부에 아래처럼 '글로벌로 URL 접근 제한 조건들 다 설정'했음에도,
      예외적 특정 상황에서만 사용될 조건을 걸어주고 싶을 경우에만 '@Secured' 또는 '@PreAuthorize'를 사용하면 됨.
    -  그런 예외적 특정 상황을 설정하지 않고 싶다면, '@Secured', '@PreAuthorize' 및 이 어노테이션들이 붙을 컨트롤러 메소드를
      애초에 작성하지 않아도 된다!

    - '클래스 SecurityConfig'의 내부에 작성한 '글로벌로 URL 접근 제한 조건들 이미 다 설정'한 코드
    (2) URL주소 '/manager/**'로 들어오는 입력은, 'ROLE_ADMIN'권한 또는 'ROLE_MANAGER' 권한이 있는
        사람만 접근 가능하도록 설정함.
        .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    (3) URL주소 '/manager/**'로 들어오는 입력은, 'ROLE_ADMIN'권한이 있는 사람만 접근 가능하도록 설정함.
        .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
     */


    //< @Secured(...) >

    //- 외부 클래스에서 아래 컨트롤러 메소드 info를 호출하여 사용하고자 할 때(=호출 전에), 사용자 권한 검사를 수행함.
    //- '컨트롤러 IndexController의 메소드 info'위에 '@Secured('ROLE_ADMIN', 'ROLE_MANAGER)'이 붙어 있으며,
    //  '이 메소드의 URL주소 @GetMapping("/info"), 즉 "/info"'에 접근하려면, '관리자 권한('ROLE_ADMIN')'
    //  또는(or) '매니저 권한(ROLE_MANGER)' 이 있는 사용자여야 그 URL주소에 접근할 수 있다!
    //- 외부 클래스에서 이 어노테이션이 붙어 있는 '컨트롤러 메소드 info'를 호출하여 사용하려면(@GetMapping(...) 등의
    //  내부의 URL주소에 접근하려면), '괄호 안에 있는 권한, 역할'을 가지고 있는 사용자여야 한다.
    //- '클래스 SecurityConfig'에 붙어 있는 '@EnbableGlobalMethodSecurity(securedEnabled = true)'와 같이 연동되어 사용됨.
    //- '클래스 단위 위에 붙는 @EnbableGlobalMethodSecurity(securedEnabled = true)'를 사용하면, 스프링은
    //  '컨트롤러 메소드 단위 위에 붙는 @Secured(...)'를 인식하고, 웹 애플리케이션에서 특정한 권한 또는 역할을 가진 사용자만이
    //  해당 메소드를 호출할 수 있도록 제한한다.


    @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    //- '관리자 권한(ROLE_ADMIN)' 또는(or) '매니저 권한(ROLE_MANGER)' 둘 중 하나의 권한이 있는 사용자만
    //  이 컨트롤러 메소드 info의 URL주소에 접근할 수 있음.
    //- *****중요*****
    // '@Secured'는 '다중 역할 권한 and 조건'은 사용할 수 없다!
    //  즉, '관리자 권한(ROLE_ADMIN)' 그리고(and) '매니저 권한(ROLE_MANGER)'을 '둘 다 동시(and)'에 갖고 있는 사용자만
    //  접근 가능하도록 설정하는 것이 불가능!
    //  그렇게 설정하고 싶은 경우에는, '@PreAuthorize'를 사용해야 한다!
    @GetMapping("/info")
    public @ResponseBody String info(){

        return "개인정보";
    }



    //============================================================================================


    //[ '스프링부트 시큐리티 5강 - 시큐리티 권한처리'강 09:20~ ]

    /*
    - '클래스 SecurityConfig' 내부에 아래처럼 '글로벌로 URL 접근 제한 조건들 다 설정'했음에도,
      예외적 특정 상황에서만 사용될 조건을 걸어주고 싶을 경우에만 '@Secured' 또는 '@PreAuthorize'를 사용하면 됨.
    -  그런 예외적 특정 상황을 설정하지 않고 싶다면, '@Secured', '@PreAuthorize' 및 이 어노테이션들이 붙을 컨트롤러 메소드를
      애초에 작성하지 않아도 된다!

    - '클래스 SecurityConfig'의 내부에 작성한 '글로벌로 URL 접근 제한 조건들 이미 다 설정'한 코드
    (2) URL주소 '/manager/**'로 들어오는 입력은, 'ROLE_ADMIN'권한 또는 'ROLE_MANAGER' 권한이 있는
        사람만 접근 가능하도록 설정함.
        .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    (3) URL주소 '/manager/**'로 들어오는 입력은, 'ROLE_ADMIN'권한이 있는 사람만 접근 가능하도록 설정함.
        .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")

     */


    //< @PreAuthroize(...) >


    //- 실무에서 가장 사용 많음. 스프링시큐리티의 '컨트롤러 메소드 수준'에서의 보안 권한 검사에 권장되는 어노테이션임.
    //  복잡한 권한 표현식을 다루어야 하는 경우 유용함.
    //- 외부 클래스에서 아래 컨트롤러 메소드 data를 호출하여 사용하고자 할 때(=호출 전에), 사용자 권한 검사를 수행함.
    //- '컨트롤러 IndexController의 메소드 data'위에 '@PreAuthorize(hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")'이
    //  붙어 있으며, '이 메소드의 URL주소 @GetMapping("/data"), 즉 "/data"'에 접근하려면, '관리자 권한('ROLE_ADMIN')'
    //  또는(or) '매니저 권한(ROLE_MANGER)' 이 있는 사용자여야 그 URL주소에 접근할 수 있다!
    //- 외부 클래스에서 이 어노테이션이 붙어 있는 '컨트롤러 메소드 data'를 호출하여 사용하려면(@GetMapping(...) 등의
    //  내부의 URL주소에 접근하려면), '괄호 안에 있는 권한, 역할'을 가지고 있는 사용자여야 한다.
    //- '클래스 SecurityConfig'에 붙어 있는 '@EnbableGlobalMethodSecurity(prePostEnabled = true)'와 같이 연동되어 사용됨.
    //- '클래스 단위 위에 붙는 @EnbableGlobalMethodSecurity(prePostEnabled = true)'를 사용하면, 스프링은
    //  '컨트롤러 메소드 단위 위에 붙는 @PreAuthroize(...)'를 인식하고, 웹 애플리케이션에서 특정한 권한 또는 역할을 가진 사용자만이
    //  해당 메소드를 호출할 수 있도록 제한한다.



    //< '@PreAuthroize(...)'와 '@Secured(...)'의 차이점 >

    //# 표현식의 유연성

    //- '@PreAuthroize'는, 'SpEL(SPRING EXPRESSION LANGUAGE)'을 사용하여 '풍부한 표현식'을 제공함.
    //  예를 들어, 역할, 권한, 커스텀 메소드, 메소드 인수 등을 접근 제한 조건으로 사용할 수 있음.
    //  e.g) @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #user.getId() == authentication.principal.id)")
    //       '관리자 권한(ROLE_ADMIN)'이 있는 사용자이거나,
    //       '일반 사용자(ROLE_USER)'이면서(=and) 그 사용자의 아이디가 '현재 컨트롤러 메소드 data의 매개변수 user'의
    //       user 아이디가 같은 경우,
    //       이 컨트롤러 메소드의 URL주소에 접근 가능함.
    //- '@Secured'는, SpEL을 사용할 수 없으며, 단순한 '역할 기반'의 접근 제어가 필요한 경우에만 사용됨.
    //  '@PreAuthorize'가 붙은 컨트롤러 메소드에는 '매개변수(아래에서의 User user)'를 사용할 수 있지만,
    //  '@Secured'에서는 컨트롤러 메소드의 매개변수 및 인수를 사용할 수 없음.


    //# 다중 권한 처리

    //- '@PreAuthroize'는, 여러 개의 권한 표현식을 조합하여 사용할 수 있음.
    //  e.g) @PreAuthroize("hasRole('ROLE_MANAGER) and hasRole('ROLE_ADMIN')")
    //      : 매니저 권한(ROLE_MANAGER) 그리고(and) 관리자 권한(ROLE_ADMIN)을 가진 사용자만
    //        이 컨트롤러 메소드의 URL주소에 접근 가능함.
    //- '@Secured'는, 여러 개의 권한 역할이 적혀 있는 경우, 무조건 '또는(or 조건)'임.
    //  '여러 권한을 모두 만족하는(=and 조건) 사용자'를 제한하는 표현식은 작성할 수 없음.


    //# 어노테이션 적용 위치

    //- '@PreAuthroize'는, '컨트롤러 메소드 레벨' 뿐 아니라, '클래스 레벨'에도 적용할 수 있음.
    //  '클래스 레벨'에 적용하면, 해당 클래스의 모든 메소드에 동일한 권한 검사가 적용됨.
    //- '@Secured'는,'컨트롤러 메소드 레벨'에 적용되며, '클래스 레벨'에는 적용할 수 없음.


    @PreAuthorize("hasRole('ROLE_MANAGER') and hasRole('ROLE_ADMIN')")
    //- '관리자 권한(ROLE_ADMIN)' 그리고(and) '매니저 권한(ROLE_MANGER)'을 '둘 다 동시(and)'에 갖고 있는 사용자만
    //  접근 가능하도록 설정함.
    //- 이 'and 조건'은 '@Secured'에서는 불가능하고, 오직 '@PreAuthorize'에서만 가능함.
    //- 물론, @PreAuthorize에서도 당연히 '또는(or)' 조건을 적용하는 것도 가능함.
    @GetMapping("/data")
    public @ResponseBody String data(User user){

        return "데이터정보";
    }



    //============================================================================================




    //============================================================================================





    //============================================================================================




}