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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    @Test
    @DisplayName("Mockito 사용하여 service 생성해보기")
    void createService() {
        MemberService memberService = mock(MemberService.class);
        StudyRepository studyRepository = mock(StudyRepository.class);

        StudyService studyService = new StudyService(memberService, studyRepository);

        assertNotNull(studyService);
    }

    @Test
    @DisplayName("Mock 어노테이션 사용하기")
    void createServiceByMockAnnotation() {
        StudyService studyService = new StudyService(memberService, studyRepository);

        assertNotNull(studyService);
    }

    @Test
    @DisplayName("메서드에서만 사용한다면 파라미터로 넘겨도된다")
    void createServiceByMockAnnotation(@Mock MemberService memberService,
                                       @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);

        assertNotNull(studyService);
    }

    @Test
    @DisplayName("Mock객체 Stubbing (stubbing이란 임의의 결과값 리턴)")
    void stubbingTest(@Mock MemberService memberService) {
        Member member = new Member();
        member.setId(1L);
        member.setEmail("dahyekim@dahye.com");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));

        assertEquals("dahyekim@dahye.com", memberService.findById(1L).get().getEmail());
    }

    @Test
    @DisplayName("Mock객체 Stubbing (stubbing이란 임의의 결과값 리턴)")
    void stubbingAnyTest(@Mock MemberService memberService) {
        Member member = new Member();
        member.setId(1L);
        member.setEmail("dahyekim@dahye.com");

        when(memberService.findById(any())).thenReturn(Optional.of(member));

        assertEquals("dahyekim@dahye.com", memberService.findById(1L).get().getEmail());
        assertEquals("dahyekim@dahye.com", memberService.findById(2L).get().getEmail());
    }

    @Test
    @DisplayName("doThrow 학습 테스트")
    void stubbingThrowTest(@Mock MemberService memberService) {
        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

        assertThrows(IllegalArgumentException.class, () -> {
            memberService.validate(1L);
        });

        memberService.validate(2L);
    }

    @Test
    @DisplayName("여러 test case stubbing 학습 테스트")
    void stubbingMultiCaseTest(@Mock MemberService memberService) {
        Member member = new Member();
        member.setId(1L);
        member.setEmail("dahyekim@dahye.com");

        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());

        assertEquals("dahyekim@dahye.com", memberService.findById(1L).get().getEmail());

        assertThrows(RuntimeException.class, () -> {
            memberService.findById(1L);
        });

        assertEquals(Optional.empty(), memberService.findById(1L));
    }

    @Test
    @DisplayName("stubbing 과제")
    void createNewStudy(@Mock MemberService memberService,
                        @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("keesun@email.com");

        Study study = new Study(10, "테스트");

        // TODO memberService 객체에 findById 메소드를 1L 값으로 호출하면 member 객체를 리턴하도록 Stubbing
        when(memberService.findById(1L))
                .thenReturn(Optional.of(member));
        // TODO studyRepository 객체에 save 메소드를 study 객체로 호출하면 study 객체 그대로 리턴하도록 Stubbing
        when(studyRepository.save(study))
                .thenReturn(study);

        studyService.createNewStudy(1L, study);

        assertNotNull(study);
        assertEquals(1L, study.getOwnerId());
    }
}