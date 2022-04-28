package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class MemberRepositoryTest {


    /**
     *
     *
     * 여기는 "Spring Data JPA"를 이용해서 만든 Repository로 테스트 하는 곳이다.
     *
     *
     */



    // spring data jpa가 인터페이스를 보고, 구현 클래스를 지가 만들어서 꽂아버린것이다.
    @Autowired MemberRepository memberRepository;

    @Test
    public void spring_data_jpa_test() throws Exception {

        System.out.println("memberRepository.getClass() = " + memberRepository.getClass());
        
        
        // given
        Member member = new Member("userA");

        Member savedMember = memberRepository.save(member);


        // when
        Member findMember = memberRepository.findById(savedMember.getId()).get();


        // then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }



    @Test
    public void basicCRUD() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        // 단건 조회 검증
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        findMember1.setUsername("member!!!!!");

        // 리스트 조회 검증
        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isEqualTo(2);

        // 카운트 검증
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        // 삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long deletedCount = memberRepository.count();
        assertThat(deletedCount).isEqualTo(0);


    }



    @Test
    public void findByUsernameAndAgeGreaterThan() throws Exception {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);
        memberRepository.findHelloBy();

        assertThat(result.get(0).getUsername()).isEqualTo("AAA");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.size()).isEqualTo(1);

    }


    @Test
    public void findHelloBy() throws Exception {
        memberRepository.findTop3HelloBy();

    }





    @Test
    public void findDistinctByUsernameAndAgeGreaterThan() throws Exception {
        memberRepository.findDistinctByUsernameAndAgeGreaterThan("AAA", 15);

    }







}
