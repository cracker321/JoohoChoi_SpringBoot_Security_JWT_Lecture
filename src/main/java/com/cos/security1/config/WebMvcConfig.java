package com.cos.security1.config;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//--------------------------------------------------------------------------------------------


//============================================================================================



@Configuration //애플리케이션 실행 시, 이것이

//[ '스프링부트 시큐리티 1강 - 환경설정'강 00:00~ ]

//< 스프링 웹 MVC 구성을 담당해주는 내장 인터페이스 WebMvcConfigurer >

//- 스프링 웹 MVC 구성을 '내 임의대로 커스터마이징'하기 위해, '스프링 내장 인터페이스 WebMvcConfiguruer'를 상속받아,
//  내가 임의의 '클래스 WebMvcConfig'를 만들어줌.
public class WebMvcConfig implements WebMvcConfigurer {





    //< '스프링 내장 인터페이스 WebMvcConfigurer의 그 내장 메소드 configureViewResolvers()'를 '오버라이딩'받음 >

    //- 이 내장 메소드를 내가 원하는 기능으로 사용하기 위해 '오버라이딩'하여, 이 내장 메소드를 '재정의'함.
    //- '뷰 리졸버'를 설정하기 위한 메소드임.
    //- 'ViewResolverRegistry 객체': '뷰 리졸버' 설정을 담당하는 '내장 클래스'임.
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

        //---------------------------------------------------------------------------------------


        //< '스프링 내장 클래스 MustacheViewResolver'의 객체를 생성함 >

        //- 'Mustache 템플릿 엔진'을 사용하기 위해, 이 뷰 리졸버 객체를 생성함.
        //  이 Mustache 뷰 리졸버 객체는, Mustache 템플릿을 처리하기 위한 뷰 리졸버임.
        MustacheViewResolver resolver = new MustacheViewResolver();



        resolver.setCharset("UTF-8");
        //- 뷰 리졸버의 문자 인코딩을 UTF-8로 설정하는 역할.
        //  이 경우, 이제 뷰 페이지에서 사용되는 텍스트 데이터의 인코딩이 UTF-8로 처리됨

        resolver.setContentType("text/html; charset=UTF-8");
        //- 뷰 리졸버가 반환하는 컨텐츠의 타입을 설정함.
        //  이 경우, 이제 'text/html 타입'으로 설정되고, 인코딩은 UTF-8로 지정됨.


        resolver.setPrefix("classpath:/templates/");
        //- 뷰 리졸버가 뷰를 찾을 때 사용하는 접두사(prefix)를 설정함.
        //  이 경우, classpath(=경로) 상의 '/templates/'(=디렉토리 templates'의 하위 파일들에서
        //  해당하는 '뷰 페이지 파일'을 찾도록 함.
        //  즉, Mustache 템플리 파일이 위치하는 경로를 '디렉토리 templates/'로 설정한다는 의미임.
        //  이러헥 하여 서버가 실행되는 동안 템플릿 파일을 자동으로 찾을 수 있도록 함.

        resolver.setSuffix(".html");
        //- 뷰 리졸버가 뷰 템플릿(페이지) 파일 찾을 때 사용할 접미사(suffix)를 설정함.
        //  뷰 템플릿(페이지) 파일의 확장자로서 '.html'을 사용하여, 서버를 실행하면서 '뷰 템플리 파일 이름'을
        //  찾을 때, 이 확장자('.html')를 추가하여 서버에서 생성된 출력물이 웹 브라우저에서 읽을 수 있는 HTML 형식으로
        //  제공됨.
        //  이 경우, '.html'로 설정되어 있으므로, 뷰 이름에 해당하는 html 파일을 찾도록 함.



        //- 최초 서버(애플리케이션)를 실행할 때, 위 모~든 과정을 다 거친 후, '스프링 컨텍스트'가
        //  이 '클래스 WebMvcCong의 인스턴스(객체)'를 생성하고, 이를 이제 사용함.
        //- 생성된 인스턴스를 통해 설정된 옵션들이 포함된 객체가 애플리케이션 전반에 전달되어, 애플리케이션 전반에
        //  일관되게 적용됨.
        //- 이렇게 구성된 MustachViewResolver를 사용하여, 서버에 있는 데이터를 클라이언트에게 HTML 형식으로 제공 가능함.

        //---------------------------------------------------------------------------------------


        registry.viewResolver(resolver);
    }

    //============================================================================================





}
