package nextstep.subway.github.application;

import lombok.RequiredArgsConstructor;
import nextstep.subway.auth.application.JwtTokenProvider;
import nextstep.subway.auth.application.MemberDetailsService;
import nextstep.subway.auth.application.UserTokenService;
import nextstep.subway.auth.application.dto.UserTokenRequest;
import nextstep.subway.auth.domain.MemberDetails;
import nextstep.subway.github.application.dto.GithubProfileResponse;
import nextstep.subway.github.domain.GithubClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GithubUserTokenServiceImpl implements UserTokenService {
    private final JwtTokenProvider jwtTokenProvider;
    private final GithubClient githubClient;
    private final MemberDetailsService memberDetailsService;

    @Override
    public String createToken(UserTokenRequest userTokenRequest) {
        if (isInvalidRequest(userTokenRequest)) {
            return null;
        }

        String githubAccessToken = githubClient.requestGithubToken(userTokenRequest.getCode());

        GithubProfileResponse githubProfileResponse = githubClient.requestGithubUserInfo(githubAccessToken);
        String email = githubProfileResponse.getEmail();

        MemberDetails memberDetails = memberDetailsService.findByEmailOrCreateMember(email);

        return jwtTokenProvider.createToken(memberDetails.getEmail());
    }

    private boolean isInvalidRequest(UserTokenRequest userTokenRequest) {
        return userTokenRequest.getCode() == null || userTokenRequest.getCode().isEmpty();
    }
}
