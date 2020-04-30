package com.dahye.test.study;

import com.dahye.test.domain.Member;
import com.dahye.test.domain.Study;
import com.dahye.test.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayName("BDD 학습")
public class BddTest {
    @Test
    @DisplayName("stubbing 과제 BDD 로 변경해보기")
    void createNewStudy(@Mock MemberService memberService,
                        @Mock StudyRepository studyRepository) {
        //given
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("keesun@email.com");

        Study study = new Study(10, "테스트");

        given(memberService.findById(1L))
                .willReturn(Optional.of(member));
        given(studyRepository.save(study))
                .willReturn(study);

        //when
        studyService.createNewStudy(1L, study);

        //then
        assertNotNull(study);
        assertEquals(1L, study.getOwnerId());

        then(memberService).should(times(1)).notify(study);
    }
}
