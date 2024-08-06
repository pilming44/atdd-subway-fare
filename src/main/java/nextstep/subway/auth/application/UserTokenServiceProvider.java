package nextstep.subway.auth.application;

import lombok.RequiredArgsConstructor;
import nextstep.subway.auth.application.dto.UserTokenRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserTokenServiceProvider {
    private final List<UserTokenService> userTokenServices;

    public String createToken(UserTokenRequest userTokenRequest) {
        for (UserTokenService userTokenService : userTokenServices) {
            String token = userTokenService.createToken(userTokenRequest);
            if (token != null) {
                return token;
            }
        }
        throw new IllegalArgumentException("요청에 적합한 UserTokenService가 없습니다.");
    }
}
