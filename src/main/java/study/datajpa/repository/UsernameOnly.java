package study.datajpa.repository;


import org.springframework.beans.factory.annotation.Value;

/**
 * Projections
 * 인터페이스 기반 Closed Projections
 *
 * 이렇게 인터페이스를 정의하면, JPA가 구현클래스를 프록시 같은 기술로 가짜를 만들어버린다.
 * 그니까 구현체는 스프링 데이터 JPA가 프록시 객체를 만들어서 값을 담아 반환해준다.
 */
public interface UsernameOnly {

//    Closed Projections
//    String getUsername();


    // Open Projections    ↓ 스프링 SPEL문법
    // 엔티티를 다 가져와서 @Value의 문법처럼 값을 담는 것
    @Value("#{target.username + ' ㅋㅋㅋ ' + target.age}")
    String getUsername();
}
