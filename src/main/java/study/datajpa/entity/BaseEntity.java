package study.datajpa.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


// 이런식으로 사용할게 몇개 없다면 클래스마다 이렇게 적어줘도 되지만,   많아진다면 xml파일로 글로벌 설정하는것도 좋다.
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseEntity extends BaseTimeEntity {

    /*

        이렇게 쓰려면 DataJpaApplication 클래스에 @EnableJpaAuditing을 적용해야 한다.

        보통 등록(created~)은 updatable = false로 설정해준다.
     */


    // 여기 두개에 값을 넣으려면 AuditorAware를 적용해야 한다.
    @CreatedBy
    @Column(updatable = false)
    private String createBy;

    @LastModifiedBy
    private String lastModifiedBy;
}
