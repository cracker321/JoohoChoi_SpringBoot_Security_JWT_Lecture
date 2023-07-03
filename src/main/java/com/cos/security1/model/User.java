package com.cos.security1.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;


//yml설정파일에서 ddl-auto: create 이렇게 설정해주면,
//서버가 실행될 때, db에 아래 '사용자 User 객체'가 '테이블 User'로 새롭게 '생성된다!!'.
@Data
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

}
