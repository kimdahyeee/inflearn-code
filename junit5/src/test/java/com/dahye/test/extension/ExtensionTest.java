package com.dahye.test.extension;

import com.dahye.test.metaAnnotation.SlowTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FindSlowTestExtension.class)
@DisplayName("extension 추가하기")
public class ExtensionTest {
    @Test
    void noSlowAnnotation() throws InterruptedException {
        Thread.sleep(2000L);
        System.out.println("slowTest annotation 없음");
    }

    @SlowTest
    void slowAnnotation() throws InterruptedException {
        Thread.sleep(2000L);
        System.out.println("slow annotation 있음");
    }
}
