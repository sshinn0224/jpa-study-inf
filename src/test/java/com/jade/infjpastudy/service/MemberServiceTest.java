package com.jade.infjpastudy.service;

import com.jade.infjpastudy.domain.Member;
import com.jade.infjpastudy.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void joinMemberTest() {
        Member member = new Member();
        member.setName("John");

        Long saveId = memberService.join(member);

        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    void duplicateMemberTest() {
        Member member1 = new Member();
        member1.setName("John");

        Member member2 = new Member();
        member2.setName("John");

        memberService.join(member1);

        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }

}