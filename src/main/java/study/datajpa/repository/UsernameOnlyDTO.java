package study.datajpa.repository;


/**
 * 클래스 기반 Projection
 */
public class UsernameOnlyDTO {

    private final String username;

    // 생성자의 파라미터 이름 기반으로 매칭한다.
    public UsernameOnlyDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
