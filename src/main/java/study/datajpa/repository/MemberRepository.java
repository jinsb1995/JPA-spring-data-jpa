package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Member;

import java.util.List;


/*
    얘는 인터페이스이고, 구현체가 없다.
    spring data jpa가 인터페이스를 보고, 구현 클래스를 지가 만들어서 꽂아버린것이다.
    JpaRepository의 <엔티티타입, PK타입> 이 중요하다.
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findHelloBy();


    void findTop3HelloBy();

    List<Member> findDistinctByUsernameAndAgeGreaterThan(String username, int age);

}
