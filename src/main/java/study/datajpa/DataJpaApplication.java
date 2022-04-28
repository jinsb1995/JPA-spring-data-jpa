package study.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// 여기 위치부터 하위 패키지까지는 spring data jpa를 사용할 수 있다.
// 위치가 달라지면 @EnableJpaRepositories필요
@SpringBootApplication
//@EnableJpaRepositories(basePackages = "study.datajpa.repository")
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}

}
