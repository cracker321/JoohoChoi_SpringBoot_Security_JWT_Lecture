package com.cos.security1.repository;

import com.cos.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


//@Repository: 이거 굳이 안 붙여도 됨. 왜냐하면, 이미 '내장 레펏 JpaRepository'를 '상속'받는 것이기 때문.
public interface UserRepository extends JpaRepository<User, Integer> {


    //--------------------------------------------------------------------------------------------

    //< 쿼리 메소드 >
    //- 실전! 스프링 데이터 JPA. pdf p30.

    //- 스프링 데이터 JPA 공식 도큐멘트 에서 예시 사용법 제시해줌.
    //  이 예시 안에 있는 거로 개발에서 필요한 웬만한 쿼리메소드는 다 작성 가능하다고 함.
    //  https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    //- '내장 레펏 JpaRepository를 상속받은 후'에, 스프링 데이터 JPA가 자체적으로 내장 인식되어 있는 메소드의 이름을
    //  개발자가 입력하면, 그 메소드를 분석하여 그 메소드에 맞게 내장 인식되어 있는 JPQL 쿼리를 자동으로 만들어서 DB에 날려줌.
    //  필요에 따라 개발자가 추가적인 키워드를 메소드 이름에 추가하여 조건(where)을 설정할 수 있음.
    //- 단순 데이터 조회, 검색 기능을 구현할 때 주로 사용됨.
    //  즉, 간단한 쿼리 정도 만들 때만 사용하고, 실무에서 자주 사용하는 것은 '@Query'를 사용하는 방법임.
    //- 장점: 개발자가 직접 쿼리문을 작성하지 않고, 내장 메소드 이름만으로 쿼리를 생성하기에 빠른 개발이 가능함.
    //- 단점: 복잡한 쿼리, 조인을 작성해야 할 경우에는 한계가 있을 수 있음. 쿼레 메소드 만으로는 모든 데이터 조작 기능 구현할 수 없음.
    //- 주의사항
    //: 쿼리 메소드에 사용된 엔티티 객체의 '필드(속성)명'이 변경된 경우, 쿼리메소드가 참조하고 있는 필드명도 변경되어야 한다!
    //  왜냐하면, 이렇게 쿼리메소드명을 변경하지 않으면, 스프링 데이터 JPA가 개발자가 작성한 쿼리 메소드를 분석할 때,
    //  해당 필드가 없다고 판단하여 예외(에러)를 발생시킴.



    //'아래 메소드 findByUsername'이 외부 클래스에서 호출되면,
    //그 때 db로 날려지는 쿼리는 'SELECT * FROM User WHERE username = 외부 클래스에서 호출할 때 넣는 인자값' 임.
    //즉, 그 DB의 여러 테이블들(=자바 엔티티들) 중 하나인 'User 테이블(=User 객체)'의 '모든 컬럼('*')'의 정보를 조회해서
    //가져오는 것임.
    //- 'username': '사용자 User 객체의 필드(속성)' = '테이블 User의 컬럼 username'
    public User findByUsername(String username);


    //--------------------------------------------------------------------------------------------

    //< 쿼리 메소드 >
    //- 실전! 스프링 데이터 JPA. pdf p30.

    //'아래 메소드 findByEmail'이 외부 클래스에서 호출되면,
    //그 때 db로 날려지는 쿼리는 'SELECT * FROM User WHERE email = 외부 클래스에서 호출할 때 넣는 인자값'임.
    //즉, 그 DB의 여러 테이블들(=자바 엔티티들) 중 하나인 'User 테이블(=User 객체)'의 모든 컬럼('*')'의 정보를 조회해서
    //가져오는 것임.
    //- 'username': '사용자 User 객체의 필드(속성)' = '테이블 User의 컬럼 username'

    public User findByEmail(String email);

}
