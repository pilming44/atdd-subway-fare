package nextstep.subway.path.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nextstep.subway.path.domain.PathSearchType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PathRequest {
    private String time;
    private Long source;
    private Long target;
    private PathSearchType type;

    public PathRequest(Long source, Long target) {
        this(null, source, target, PathSearchType.DURATION);
    }

    public PathRequest(Long source, Long target, PathSearchType type) {
        this(null, source, target, type);
    }
}
