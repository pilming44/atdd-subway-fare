package nextstep.subway.auth.application;

import lombok.RequiredArgsConstructor;
import nextstep.subway.auth.application.dto.GithubTokenRequest;
import nextstep.subway.auth.application.dto.MemberTokenRequest;
import nextstep.subway.auth.application.dto.TokenResponse;
import nextstep.subway.auth.application.dto.UserTokenRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final UserTokenServiceProvider userTokenService;

    public TokenResponse createToken(MemberTokenRequest request) {
        UserTokenRequest userTokenRequest = UserTokenRequest.fromMemberTokenRequest(request);
        return new TokenResponse(userTokenService.createToken(userTokenRequest));
    }

    public TokenResponse createGithubToken(GithubTokenRequest request) {
        UserTokenRequest userTokenRequest = UserTokenRequest.fromGithubTokenRequest(request);
        return new TokenResponse(userTokenService.createToken(userTokenRequest));
    }
}
