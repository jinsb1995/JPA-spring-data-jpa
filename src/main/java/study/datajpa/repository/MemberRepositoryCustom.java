package study.datajpa.repository;

import study.datajpa.entity.Member;

import java.util.List;

// 커스텀 인터페이스를 만들고 그에대한 구현체도 만들어준다. MemberRepositoryImpl
// QueryDSL 쓸 때 많이 사용한다.
public interface MemberRepositoryCustom {

    List<Member> findMemberCustom();

}
