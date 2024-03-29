package study.datajpa.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository {

    // 이렇게 사용해도 된다.
//    @PersistenceContext private EntityManager em;

    private final EntityManager em;


    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    public void delete(Member member) {
        em.remove(member);
    }


    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    // optional
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public long count() {
        return em.createQuery("select count(m) from Member m", Long.class)
                .getSingleResult();
    }


    public Member find(Long id) {
        return em.find(Member.class, id);
    }


    // 회원 이름과 나이 기준으로 조회
    public List<Member> findByUsernameAndAgeGreaterThan(String username, int age) {
        return em.createQuery("select m from Member m where m.username = :username and m.age > :age")
                .setParameter("username", username)
                .setParameter("age", age)
                .getResultList();
    }


    // named query
    public List<Member> findByUsername(String username) {
        return em.createNamedQuery("Member.findByUsername", Member.class)
                .setParameter("username", username)
                .getResultList();
    }




    // 페이징 JPA |  나이 = 10,  name desc,  첫번째 페이지, 페이지당 보여줄 데이터는 3건
    // offset은 몇번쨰 부터,    limit는 몇개를 가져올거냐
    public List<Member> findByPage(int age, int offset, int limit) {
        return em.createQuery("select m from Member m where m.age = :age order by m.username desc", Member.class)
                .setParameter("age", age)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public long totalCount(int age) {
        return em.createQuery("select count(m) from Member m where m.age = :age", Long.class)
                .setParameter("age", age)
                .getSingleResult();
    }




    // 벌크성 수정 쿼리
    public int bulkAgePlus(int age) {
        return em.createQuery(
                "update Member m" +
                        " set m.age = m.age + 1" +
                        " where m.age >= :age")
                    .setParameter("age", age)
                    .executeUpdate();
    }




}
