package com.dahye.test;

import org.junit.jupiter.api.*;

@DisplayName("junit5 before, after")
public class Junit5Test {

    @Test
    @Disabled
    void create() {
        System.out.println("create");
    }

    @Test
    void create2() {
        System.out.println("create2");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("before all");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("after all");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("before each");
    }

    @AfterEach
    void afterEach() {
        System.out.println("after each");
    }
}
