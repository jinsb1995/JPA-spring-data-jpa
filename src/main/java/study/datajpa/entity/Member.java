package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"})
@NamedQuery(
        name = "Member.findByUsername",
        query = "select m from Member m where m.username = :username"
)
@NamedEntityGraph(name = "Member.All", attributeNodes = @NamedAttributeNode("team"))  // @EntityGraph("Member.ALl") 이렇게 사용하면 된다.
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null) {
            changeTeam(team);
        }
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    // 양방향 연관관계 한번에 처리(연관관계 편의 메서드) - 멤버는 팀을 바꿀 수 있다.
    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);    // team에 있는 멤버에다가도 나를 넣어줘야한다.
    }
}
