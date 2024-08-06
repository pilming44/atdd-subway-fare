package nextstep.subway.path.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewPathRequest {
    private Long source;
    private Long target;
    private PathSearchType type;
}
