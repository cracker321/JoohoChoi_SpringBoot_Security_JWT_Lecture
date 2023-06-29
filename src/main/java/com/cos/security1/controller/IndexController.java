package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //'뷰'를 리턴
public class IndexController {



    //---------------------------------------------------------------------------------------


    //============================================================================================



    @GetMapping({"", "/"})
    public @ResponseBody String index(){


        return "index";
        //웹브라우저 URL주소에 'localhost:8080'를 입력하면,
        //이제 화면에 'index'라는 글자가 보이게 됨.
    }

    //< '@ReponseBody' >

    //- '@RepsnseBody'가 붙어 있는 현재 컨트롤러 메소드의 '리턴값'은 HTTP 응답 본문에 그대로 포함됨.
    //  즉, 별도의 '뷰 View'를 생성하거나 관리하지 않아도 되고, 그 반환값이 자바 객체라면 JSON 형식으로 직렬화
    //  변환되어, 반환값이 그 자체로 HTTP 응답 본문으로 사용되어 클라이언트에게 전달됨.
    //- 여기서는 '회원가입 완료됨!'문자열이 JSON 형식으로 변환되어 HTTP 응답 본문에 포함되어 클라이언트에게 전송됨.
    //- 이를 통해 개발자는 별도의 뷰 html 페이지를 생성하거나 관리하지 않고도 데이터를 손쉽게 HTTP 응답으로
    //  사용자에게 넘길 수 있음.


    //============================================================================================

    //[ '스프링부트 시큐리티 2강 - 시큐리티 설정'강 00:00~ ]


    @GetMapping("/user")
    public @ResponseBody String user(){

        return "user";
        //웹브라우저 URL주소에 'localhost:8080/user'를 입력하면,
        //이제 화면에 'user'라는 글자가 보이게 됨.
    }



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
    @GetMapping("/login")
    public String login(){

        return "loginForm";
    }


    //============================================================================================


    @GetMapping("/join")
    public @ResponseBody String join(){

        return "join";
    }


    //============================================================================================

    @GetMapping("/joinProc")
    public @ResponseBody String joinProc(){

        return "회원가입 완료됨!";
    }

    //============================================================================================



}