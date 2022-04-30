package study.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;
import java.util.UUID;

// 여기 위치부터 하위 패키지까지는 spring data jpa를 사용할 수 있다.
// 위치가 달라지면 @EnableJpaRepositories필요
@SpringBootApplication
//@EnableJpaRepositories(basePackages = "study.datajpa.repository")
@EnableJpaAuditing  // 등록일, 수정일 관련 설정
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}



	@Bean
	public AuditorAware<String> auditorProvider() {
		// 실무에서는 session정보나, 스프링 시큐리티 로그인 정보에서 유저의 ID를 가져온다.
		// 그러면 @CreateBy, @LastModifiedBy에 값이 자동으로 채워진다.
		return () -> Optional.of(UUID.randomUUID().toString());

//		인터페이스에서 메서드가 하나면 이렇게 람다로 사용할 수 있다.
//		return new AuditorAware<String>() {
//			@Override
//			public Optional<String> getCurrentAuditor() {
//				return Optional.of(UUID.randomUUID().toString());
//			}
//		};
	}

}
