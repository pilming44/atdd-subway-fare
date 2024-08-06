package nextstep.subway.line.application.dto;

import lombok.*;
import nextstep.subway.line.domain.Newline;
import nextstep.subway.station.application.dto.StationResponse;
import nextstep.subway.station.domain.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NewlineResponse {
    private Long id;
    private String name;
    private String color;
    private List<StationResponse> stations = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public List<StationResponse> getStations() {
        return stations;
    }

    public static NewlineResponse from(Newline line) {
        List<Station> stations = line.getStations();
        List<StationResponse> stationResponse = stations.stream()
                .map(StationResponse::from)
                .collect(Collectors.toList());
        NewlineResponse lineResponse = new NewlineResponse(
                line.getId(),
                line.getName(),
                line.getColor(),
                stationResponse
        );
        return lineResponse;
    }
}
