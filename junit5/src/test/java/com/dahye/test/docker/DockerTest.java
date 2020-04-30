package com.dahye.test.docker;

import com.dahye.test.domain.Member;
import com.dahye.test.domain.Study;
import com.dahye.test.domain.StudyStatus;
import com.dahye.test.member.MemberService;
import com.dahye.test.study.StudyRepository;
import com.dahye.test.study.StudyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

/**
 * @reference https://www.testcontainers.org/modules/databases/jdbc/
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Testcontainers
@DisplayName("docker를 이용해 DB연동 테스트")
public class DockerTest {
    @Mock
    MemberService memberService;
    @Autowired
    StudyRepository studyRepository;

    @Container
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
            .withDatabaseName("studytest");

    @BeforeEach
    void beforeEach() {
        studyRepository.deleteAll();
    }

    @Test
    @DisplayName("create new study")
    void createNewStudy() {
        //given
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("keesun@email.com");

        Study study = new Study(10, "테스트");

        given(memberService.findById(1L))
                .willReturn(Optional.of(member));

        //when
        studyService.createNewStudy(1L, study);

        //then
        assertNotNull(study);
        assertEquals(1L, study.getOwnerId());

        then(memberService).should(times(1)).notify(study);
    }

}
