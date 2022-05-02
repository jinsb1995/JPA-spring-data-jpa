package study.datajpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item implements Persistable<String> {

    /**    새로운 엔티티를 판단하는 기본 전략     */
    
    
//     이 GeneratedValue로 인해 id가 생기게 되는 시점은 JPA안에 들어가서 persist()까지 들어가면 그 때 id가 생긴다.
//     1. 식별자가 객체일 떄 null로 판단
//    @Id @GeneratedValue
//    private Long id;

//     2. 식별자가 자바 기본 타입일 때 0으로 판단.
//    private long id;


    // 이렇게 하면 1에서 걸리지 않으므로 save()를 호출했을 떄 merge()로 넘어간다.
    @Id
    private String id;



    public Item(String id) {
        this.id = id;
    }


    // 이 CreatedDate는 persist()가 되기 전에 호출된다.
    @CreatedDate
    private LocalDateTime createdDate;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        // 어떤 조건일 때 true냐 false냐에 대한 로직을 직접 짜야한다.
        // createdDate는 persist가 호출되고 insert가 일어나기 전에 생성이 된다.
        return createdDate == null;
    }
}
