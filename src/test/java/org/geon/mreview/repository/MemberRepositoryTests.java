package org.geon.mreview.repository;

import lombok.extern.log4j.Log4j2;
import org.geon.mreview.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    public void insertMember() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder()
                    .email("r" + i + "@geon.com")
                    .pw("1111")
                    .nickname("reviewer" + i)
                    .build();
            memberRepository.save(member);
        });
    }

    @Commit
    @Transactional
    @Test
    public void testDeleteMember() {
        Long mid = 2L;

        Member member = Member.builder().mid(mid).build();

        memberRepository.deleteById(mid);
        reviewRepository.deleteByMember(member);
    }
}
