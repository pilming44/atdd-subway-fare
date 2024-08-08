package nextstep.subway.member.application;

import lombok.RequiredArgsConstructor;
import nextstep.subway.auth.application.dto.LoginMember;
import nextstep.subway.exception.NoSuchMemberException;
import nextstep.subway.member.application.dto.MemberRequest;
import nextstep.subway.member.application.dto.MemberResponse;
import nextstep.subway.member.domain.Member;
import nextstep.subway.member.domain.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse createMember(MemberRequest request) {
        Member member = memberRepository.save(request.toMember());
        return MemberResponse.of(member);
    }

    public MemberResponse findMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchMemberException());
        return MemberResponse.of(member);
    }

    @Transactional
    public void updateMember(Long id, MemberRequest param) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchMemberException());
        member.update(param.toMember());
    }

    @Transactional
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    public Member findMemberByEmailOrThrow(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchMemberException());
    }

    public MemberResponse findMe(LoginMember loginMember) {
        return memberRepository.findByEmail(loginMember.getEmail())
                .map(it -> MemberResponse.of(it))
                .orElseThrow(() -> new NoSuchMemberException());
    }

    @Transactional
    public Member findByEmailOrCreateMember(String email) {
        try {
            return findMemberByEmailOrThrow(email);
        } catch (NoSuchMemberException e) {
            createMember(new MemberRequest(email));
            return findMemberByEmailOrThrow(email);
        }
    }
}