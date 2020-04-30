package com.dahye.test.learningtest.metaAnnotation;

import org.junit.jupiter.api.DisplayName;

@DisplayName("메타 애노테이션 만들기")
public class MetaAnnotationTest {

    @FastTest
    void fast() {
        System.out.println("fast test");
    }

    @SlowTest
    void slow() {
        System.out.println("slow test");
    }
}
