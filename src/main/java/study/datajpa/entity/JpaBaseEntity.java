package study.datajpa.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

// 이 객체를 상속하는게 아니라, 속성!만 공유해주는 용도로 사용한다.
@MappedSuperclass
@Getter
public class JpaBaseEntity {

    @Column(updatable = false)  // 이 값은 변경되지 않는다.
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // em.persist하기 전에 발생하는 이벤트
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdDate = now;
        this.updatedDate = now;
    }


    @PreUpdate
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }

}
