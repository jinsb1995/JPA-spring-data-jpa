package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDTO;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired TeamRepository teamRepository;
    @PersistenceContext EntityManager em;



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



    // namedQuery


    @Test
    public void testNamedQuery() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);


        List<Member> result = memberRepository.findByUsername("AAA");
        Member findMember = result.get(0);
        assertThat(findMember).isEqualTo(m1);

    }



    @Test
    public void testQuery() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);


        List<Member> result = memberRepository.findByUsername("AAA");
        Member findMember = result.get(0);
        assertThat(findMember).isEqualTo(m1);

    }


    @Test
    public void findUsernameList() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);


        List<String> usernameList = memberRepository.findUsernameList();
        for (String s : usernameList) {
            System.out.println("s = " + s);
        }
    }


    @Test
    public void findMemberDTO() {
        Team team = new Team("teamA");
        teamRepository.save(team);

        Member m1 = new Member("AAA", 10);
        m1.setTeam(team);
        memberRepository.save(m1);


        List<MemberDTO> findMember = memberRepository.findMemberDTO();
        for (MemberDTO memberDTO : findMember) {
            System.out.println("memberDTO = " + memberDTO);
        }
    }





    @Test
    public void findByNamesWithCollectionParameterBinding() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("BBB", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> result = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));
        for (Member member : result) {
            System.out.println("member = " + member);
        }
    }



    // 반환 타입 테스트
    @Test
    public void returnType() {
        Member m1 = new Member("AAA", 10);
        Member m2 = new Member("AAA", 20);
        memberRepository.save(m1);
        memberRepository.save(m2);


        // 컬렉션일 떄 - null이 아닌 empty Collection을 반환해준다.
//        List<Member> result = memberRepository.findListByUsername("asdasf");
//        System.out.println("result.size() = " + result.size());


        // 단건 - 데이터가 없으면 exception이 터지는데,  JPA가 자체적으로 try-catch로 잡아서 null을 반환해준다.
//        Member result = memberRepository.findMemberByUsername("asdasf");
//        System.out.println("result = " + result);


        // 단건 (Optional) - 없으면
        Optional<Member> result = memberRepository.findOptionalByUsername("AAA");
        System.out.println("result = " + result);


    }




    @Test
    public void paging() {
        // given

        for (int i = 0; i < 5; i++) {
            memberRepository.save(new Member("member" + i, 10));
        }


        int age = 10;

        // 페이지를 0부터 시작한다.
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));


        // when
        // 이건 지금 엔티티를 반환하고 있어서 아래의 작업이 필요하다.
        Page<Member> page = memberRepository.findByAge(age, pageRequest);

        // 내부에 있는걸 다른걸로 바꿔서 반환하는 것
        Page<MemberDTO> map = page.map(member -> new MemberDTO(member.getId(), member.getUsername(), null));

        // then
        List<Member> content = page.getContent();

        System.out.println("page.getTotalElements() = " + page.getTotalElements());
        System.out.println("page = " + page);
//        assertThat(content.size()).isEqualTo(3);
//        assertThat(page.getTotalElements()).isEqualTo(5);
//        assertThat(page.getNumber()).isEqualTo(0);
//        assertThat(page.getTotalPages()).isEqualTo(2);
//        assertThat(page.isFirst()).isTrue();
//        assertThat(page.hasNext()).isTrue();


    }







    @Test
    public void bulkUpdate() throws Exception {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 19));
        memberRepository.save(new Member("member3", 20));
        memberRepository.save(new Member("member4", 21));
        memberRepository.save(new Member("member5", 40));


        // when
        // 1. 이렇게 벌크 연산으로 DB에 바로 때려버리면
        int resultCount = memberRepository.bulkAgePlus(20);


        // 아직 남아있는 변경되지 않은 내용이 DB에 반영이 되는것이다.
//        em.flush();
//        // 영속성 컨텍스트를 비워준다.
//        em.clear();



        // 2. 여기서 조회할떄는 영속성 컨텍스트에서 가져오기때문에 아직 반영이 안된 상태이다.
        List<Member> result = memberRepository.findByUsername("member5");
        Member member = result.get(0);

        System.out.println("member = " + member);

        // then
        assertThat(resultCount).isEqualTo(3);

    }





    @Test
    public void findMemberLazy() throws Exception {
        // given
        // member1 -> teamA 참조
        // member2 -> teamB 참조

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 10, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);


        em.flush();
        em.clear();


        // when N(result = 2) + 1
        // select Member 1
        List<Member> members = memberRepository.findEntityGraphByUsername("member1");

        for (Member member : members) {
            System.out.println("member = " + member.getUsername());
            // select Team
            System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
            System.out.println("member.getTeam().getClass() = " + member.getTeam().getClass());
        }


        // then

    }






    // Hint
    @Test
    public void queryHint() throws Exception {
        // given
        // jpa는 readonly라는 기능이 있다.
        Member member1 = new Member("member1", 10);
        memberRepository.save(member1);
        em.flush();
        em.clear();

        //when
        Member findMember = memberRepository.findReadOnlyByUsername("member1");
        findMember.setUsername("member2");

        em.flush();

    }
    
    
    
    @Test
    public void lock() {
        //given
        Member member = new Member("member1", 10);
        memberRepository.save(member);
        em.flush();
        em.clear();
        
        //when
        List<Member> result = memberRepository.findLockByUsername("member1");
    }
    
    
    
    // 사용자 정의 Repository
    @Test
    public void callCustom() {
        List<Member> memberCustom = memberRepository.findMemberCustom();
    }






}
