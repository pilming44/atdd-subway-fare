package nextstep.subway.path.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nextstep.subway.station.application.dto.StationResponse;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PathResponse {
    private List<StationResponse> stations = new ArrayList<>();
    private Long distance;
    private Long duration;
    private Long fare;
}
