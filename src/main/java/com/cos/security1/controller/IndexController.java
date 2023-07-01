package com.cos.security1.controller;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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



    @GetMapping({"", "/"})
    public String index(){


        return "index";
        //웹브라우저 URL주소에 'localhost:8080'를 입력하면,
        //이제 화면에 'index.html' 속의 코드 구현 내용들이 보이게 됨.
    }


    //============================================================================================

    //[ '스프링부트 시큐리티 2강 - 시큐리티 설정'강 00:00~ ]


    @GetMapping("/user")
    public @ResponseBody String user(){

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
    public String joinForm(){

        return "joinForm";
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

    //< @Secured(...) >

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

    //< @PreAuthroize(...) >

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