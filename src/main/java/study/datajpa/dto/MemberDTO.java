package study.datajpa.dto;


import lombok.Data;
import study.datajpa.entity.Member;

@Data
public class MemberDTO {

    // DTO는 엔티티를 직접 바라봐도 괜찮다.


    private Long id;
    private String username;
    private String teamName;

    public MemberDTO(Long id, String username, String teamName) {
        this.id = id;
        this.username = username;
        this.teamName = teamName;
    }

    public MemberDTO(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
    }

}
