package nextstep.subway.path.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPathRequest {
    private Long source;
    private Long target;
    private PathSearchType type;
}
