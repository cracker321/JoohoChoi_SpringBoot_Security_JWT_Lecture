package com.cos.security1.model;

class OrderBuilderPracticeTest {


    //- '@Builder 메소드'에 포함된 매개변수들 중 '일부만 선택적으로 빼와서' 외부 다른 클래스(테스트 OrderTest)에서
    //  그 매개변수로만 Order 객체를 생성해도 된다!
    //- '매개변수의 순서': '마구잡이로 아무렇게나 해도 된다'!!
    //- '@Builder 메소드에는 포함시켰으나, 외부 클래스에서 그 객체를 만들 때 필요 없는 매개변수'는 '마음대로 안 넣어도 된다'!!

    OrderBuilderPractice orderBuilderPractice = OrderBuilderPractice.builder()
            .productName("상품명")
            .quantity(6).build();
}