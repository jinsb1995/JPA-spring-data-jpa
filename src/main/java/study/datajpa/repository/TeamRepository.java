package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Team;


// @Repository가 없어도 JPA가 알아서 인식해준다.
public interface TeamRepository extends JpaRepository<Team, Long > {
}
