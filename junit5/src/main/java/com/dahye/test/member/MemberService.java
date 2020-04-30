package com.dahye.test.member;

import com.dahye.test.domain.Member;
import com.dahye.test.domain.Study;

import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long memberId);

    void validate(Long memberId);

    void notify(Study newstudy);

    void notify(Member member);
}
