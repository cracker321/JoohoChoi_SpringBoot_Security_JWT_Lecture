package com.cos.security1.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;


//yml설정파일에서 ddl-auto: create 이렇게 설정해주면,
//서버가 실행될 때, db에 아래 '사용자 User 객체'가 '테이블 User'로 새롭게 '생성된다!!'.
@Data
@NoArgsConstructor
@Entity
public class User {


    //아래 '필드(속성)'이, DB에서는 '컬럼'이 된다!
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;
    private String email;
    private String role;

    private String provider;
    private String providerId;

    @CreationTimestamp
    private Timestamp createDate;





    //============================================================================================


    //[ '스프링부트 시큐리티 9강 - 구글 로그인 및 자동 회원가입 진행 완료'강 10:40~ ]

    //- '클래스 PrincipalOauth2UserSrvice'에서 '강제 회원가입 진행 실습'을 위해 '임의의 사용자 User 객체'를 만들기 위해서,
    //  여기 '사용자 엔티티 User 객체'에

    @Builder
    public User(int id, String username, String password, String email, String role, String provider, String providerId, Timestamp createDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
    }

}
