package nextstep.subway.auth.ui;

import nextstep.subway.auth.application.TokenService;
import nextstep.subway.auth.application.dto.GithubTokenRequest;
import nextstep.subway.auth.application.dto.MemberTokenRequest;
import nextstep.subway.auth.application.dto.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {
    private TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/login/token")
    public ResponseEntity<TokenResponse> createToken(@RequestBody MemberTokenRequest request) {
        TokenResponse response = tokenService.createToken(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login/github")
    public ResponseEntity<TokenResponse> createGithubToken(@RequestBody GithubTokenRequest request) {
        TokenResponse response = tokenService.createGithubToken(request);

        return ResponseEntity.ok(response);
    }
}