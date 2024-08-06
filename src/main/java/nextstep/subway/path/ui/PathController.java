package nextstep.subway.path.ui;

import nextstep.subway.path.application.PathService;
import nextstep.subway.path.application.dto.NewPathRequest;
import nextstep.subway.path.application.dto.NewPathResponse;
import nextstep.subway.path.application.dto.PathRequest;
import nextstep.subway.path.application.dto.PathResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paths")
public class PathController {
    private final PathService pathService;

    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    @GetMapping
    public ResponseEntity<PathResponse> getPath(PathRequest pathRequest) {
        return ResponseEntity.ok().body(pathService.getPathOrThrow(pathRequest));
    }

    @GetMapping("/distance")
    public ResponseEntity<NewPathResponse> getDistancePath(NewPathRequest pathRequest) {
        return ResponseEntity.ok().body(pathService.getDistancePathOrThrow(pathRequest));
    }
    @GetMapping("/duration")
    public ResponseEntity<NewPathResponse> getDurationPath(NewPathRequest pathRequest) {
        return ResponseEntity.ok().body(pathService.getDurationPathOrThrow(pathRequest));
    }
}
