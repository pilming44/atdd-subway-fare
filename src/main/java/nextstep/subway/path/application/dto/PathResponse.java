package nextstep.subway.path.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nextstep.subway.path.domain.FareCondition;
import nextstep.subway.station.application.dto.StationResponse;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PathResponse {
    private List<StationResponse> stations = new ArrayList<>();
    private Long distance;
    private Long duration;
    private Long fare;

    private PathResponse(List<StationResponse> stations, Long distance, Long duration, Long fare) {
        this.stations = stations;
        this.distance = distance;
        this.duration = duration;
        this.fare = fare;
    }

    public static PathResponse of(List<StationResponse> stations, FareCondition fareCondition, Long fare) {
        Long distance = fareCondition.getDistance();
        Long duration = fareCondition.getDuration();
        return new PathResponse(stations, distance, duration, fare);
    }
}
