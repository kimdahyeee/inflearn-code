package com.dahye.test.learningtest.order;

import org.junit.jupiter.api.*;

@DisplayName("테스트 순서 설정하기(TestMethodOrder), 테스트 인스턴스 공유 (TestInstance)")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderTest {
    int value = 1;

    @Order(1)
    @Test
    void test1() {
        System.out.println("1 : " + value++);
    }

    @Order(3)
    @Test
    void test3() {
        System.out.println("3 : " + value++);
    }

    @Order(2)
    @Test
    void test2() {
        System.out.println("2 : " + value++);
    }
}
