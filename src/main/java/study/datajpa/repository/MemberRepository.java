package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDTO;
import study.datajpa.entity.Member;

import java.util.List;
import java.util.Optional;


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


    // namedQuery  스프링 데이터 JPA는 @Query를 없애도 동작한다.
    // 위의 제네릭이 Member에 가서         1. NamedQuery를 먼저 찾는데,       2. 만약 없다면, 메서드 명을 기준으로 쿼리를 만들어준다.
//    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);


    @Query(value = "select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);



    // 값 하나만 조회
    @Query("select m.username from Member m")
    List<String> findUsernameList();


    // DTO로 직접 조회
    @Query("select new study.datajpa.dto.MemberDTO(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDTO> findMemberDTO();



    // 이름 기반 - 컬렉션 파라미터 바인딩
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);


    List<Member> findListByUsername(String username);     // 컬렉션
    Member findMemberByUsername(String username);         // 단건
    Optional<Member> findOptionalByUsername(String username);   // 단건 Optional



    // spring data jpa - 페이징
//    Page<Member> findByAge(int age, Pageable pageable);

    @Query(value = "select m from Member m"
            /*, countQuery = "select count(m) from Member m"*/)
    Page<Member> findByAge(int age, Pageable pageable);


}
