package nextstep.subway.path.application.dto;

import lombok.Getter;
import lombok.Setter;
import nextstep.subway.station.application.dto.StationResponse;
import nextstep.subway.station.domain.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PathResponse {
    private List<StationResponse> stations = new ArrayList<>();
    private Long distance;
    private Long duration;

    private PathResponse(List<StationResponse> stations, Long distance, Long duration) {
        this.stations = stations;
        this.distance = distance;
        this.duration = duration;
    }

    public static PathResponse of(List<Station> stations, Long distance, Long duration) {
        List<StationResponse> collect = stations.stream()
                .map(StationResponse::from)
                .collect(Collectors.toList());
        return new PathResponse(collect, distance, duration);
    }
}
