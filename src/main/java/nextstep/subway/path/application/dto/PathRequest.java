package nextstep.subway.path.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PathRequest {
    private Long source;
    private Long target;
    private PathSearchType type;

    public PathRequest(Long source, Long target) {
        this(source, target, PathSearchType.DURATION);
    }


}
