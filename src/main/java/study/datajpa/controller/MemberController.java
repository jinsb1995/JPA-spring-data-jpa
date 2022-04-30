package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }


    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {
        // 스프링 데이터 JPA가 들어온 PK(id)값으로 컨버팅을 해줘서 이렇게 사용해도 된다.
        // 권장하지는 않는다!
        return member.getUsername();
    }




    @PostConstruct
    public void init() {
        memberRepository.save(new Member("userA"));
    }
}
