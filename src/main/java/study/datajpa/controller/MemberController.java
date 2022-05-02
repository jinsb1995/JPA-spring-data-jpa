package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDTO;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

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


    @GetMapping("/members")
    public Page<MemberDTO> list(@PageableDefault(size = 5, sort = "username") Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);

        return page
//                .map(m -> new MemberDTO(m));
                .map(MemberDTO::new);
    }


//    @PostConstruct
    public void init() {
        for (int i = 0; i < 100; i++) {
             memberRepository.save(new Member("user"+i, i));
        }
    }
}
