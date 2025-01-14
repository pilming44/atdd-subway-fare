package nextstep.subway.path.ui;

import lombok.RequiredArgsConstructor;
import nextstep.subway.auth.application.User;
import nextstep.subway.auth.config.NullableAuthenticationPrincipal;
import nextstep.subway.path.application.PathService;
import nextstep.subway.path.application.dto.PathRequest;
import nextstep.subway.path.application.dto.PathResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paths")
@RequiredArgsConstructor
public class PathController {
    private final PathService pathService;

    @GetMapping
    public ResponseEntity<PathResponse> getPath(@NullableAuthenticationPrincipal User user, PathRequest pathRequest) {
        return ResponseEntity.ok().body(pathService.getPathOrThrow(user, pathRequest));
    }
}
