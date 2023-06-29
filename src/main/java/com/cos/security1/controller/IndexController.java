package com.cos.security1.controller;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller //'뷰'를 리턴
public class IndexController {

    private final UserRepository userRepository;

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


    //< 회원가입 >

    @PostMapping("/join")
    public @ResponseBody String join(User user){

        //순서 1) '뷰 joinForm.html 내부의 폼 form 객체'를 통해 '사용자 User 객체'가
        //       화면에 떠 있는 '신규회원가입에 필요한 본인 정보 (아이디, 비밀번호, 이메일)'을 입력하고
        //       '제출, 확인 등의 버튼'을 누르면,
        //순서 2) '뷰 joinForm.html 내부의 action 태그 정보(action="/join")'에 '/join'으로 되어있으니,
        //       사용자가 입력한 데이터(아이디, 비밀번호, 이메일)가 URL 주소 'localhost:8080/join'으로 넘어가게 되고,
        //순서 3) 그것이 유기적으로 연결되어 서버단인 여기 @PostMapping("/join")으로 넘어와서,
        //       '사용자 User 객체'가 폼 form 객체에 입력한 정보(아이디, 비밀번호, 이메일) 이외에 더 존재해야 하는
        //       '사용자 User 객체의 필드(속성)'인 '필드 Role'의 속성값을 바로 여기 '컨트롤러 메소드 join' 에서
        //       추가해주고,
        //순서 4) '레펏 UserRepository'를 통해 여기서 그 '사용자 User 객체 정보들'을 DB에 저장시키는 것이 순서가 된다!


        //DB에, '테이블 User의 컬럼 Role'의 '행(데이터) 값'으로 'ROLE_USER'가 입력되게 되는 것임.
        user.setRole("ROLE_USER");



        userRepository.save(user);

        return "join";
    }


    //============================================================================================



}