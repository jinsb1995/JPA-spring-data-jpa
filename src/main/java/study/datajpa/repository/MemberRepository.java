package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDTO;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;


/*
    얘는 인터페이스이고, 구현체가 없다.
    spring data jpa가 인터페이스를 보고, 구현 클래스를 지가 만들어서 꽂아버린것이다.
    JpaRepository의 <엔티티타입, PK타입> 이 중요하다.
 */
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

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


    // bulkUpdate,  @Modifying이 없으면 getSingleResult()가 호출된다.
    // 벌크 연산은 영속성 컨텍스트에서 관리하는걸 무시하고 바로 DB에 때려버린다.
    //
    @Modifying(clearAutomatically = true)   // 쿼리가 나가고 난 다음에 em.clear()를 호출해준다.
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);





    // fetch join
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberByJoin();

    @Override
    @EntityGraph(attributePaths = {"team"})  // @EntityGraph는 그냥 fetch join이라고 생각하자
    List<Member> findAll();  // 지연로딩이라서 Member만 조회가 될텐데 @EntityGraph를 사용하면 연관관계 엔티티도 한방 쿼리로 가져온다.


    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();


    @EntityGraph(attributePaths = {"team"})
    List<Member> findEntityGraphByUsername(@Param("username") String username);




    // Hint - 영속성 컨텍스트는 dirty checking때문에 findById를 해도 수정할 수 있게 원본과 snapshot을 갖고있는데 이게 비효율적이다.
    // 변경감지를 위한 snapshot
    // 그래서 이렇게 힌트를 작성하면 조회한 원본만 가지고 있을 수 있기 때문에 수정본을 가지고 있을 비용을 줄일 수 있다.
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);



    // lock
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);


}
