package com.dahye.test.learningtest.extension;

import com.dahye.test.learningtest.metaAnnotation.SlowTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@DisplayName("RegisterExtension을 사용하여 변수 커스터마이징 하기")
public class RegisterExtensionTest {

    @RegisterExtension
    static FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(2000L);

    @Test
    void noSlowAnnotation1() throws InterruptedException {
        Thread.sleep(2000L);
        System.out.println("slowTest annotation 없음 : 2000L");
    }

    @Test
    void noSlowAnnotation2() throws InterruptedException {
        Thread.sleep(1000L);
        System.out.println("slowTest annotation 없음 : 1000L");
    }

    @SlowTest
    void slowAnnotation() throws InterruptedException {
        Thread.sleep(2000L);
        System.out.println("slow annotation 있음");
    }
}
