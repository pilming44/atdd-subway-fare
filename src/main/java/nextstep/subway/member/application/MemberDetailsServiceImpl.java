package nextstep.subway.member.application;

import lombok.RequiredArgsConstructor;
import nextstep.subway.auth.application.MemberDetailsService;
import nextstep.subway.auth.domain.MemberDetails;
import nextstep.subway.member.domain.Member;
import nextstep.subway.member.domain.MemberDetailsImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsServiceImpl implements MemberDetailsService {
    private final MemberService memberService;

    @Override
    public MemberDetails findByEmailOrCreateMember(String email) {
        Member member = memberService.findByEmailOrCreateMember(email);

        return new MemberDetailsImpl(member.getEmail(), member.getPassword());
    }
}
