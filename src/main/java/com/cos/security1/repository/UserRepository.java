package com.cos.security1.repository;

import com.cos.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


//@Repository: 이거 굳이 안 붙여도 됨. 왜냐하면, 이미 '내장 레펏 JpaRepository'를 '상속'받는 것이기 때문.
public interface UserRepository extends JpaRepository<User, Integer> {



}
