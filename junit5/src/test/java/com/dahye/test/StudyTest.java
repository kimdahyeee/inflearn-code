package com.dahye.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudyTest {

    @Test
    void create() {
        Study study = new Study(1);
        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태 값이 DRAFT여야 한다.")
        );
    }

    @Test
    @DisplayName("exception 처리 및 메시지 확인")
    void exception() {
        IllegalArgumentException exception
                = assertThrows(IllegalArgumentException.class, () -> new Study(-10));

        assertEquals("limit은 0보다 커야 한다", exception.getMessage());
    }
}