package nextstep.subway.path.domain;

import lombok.Getter;
import nextstep.subway.line.domain.Section;
import nextstep.subway.line.domain.Sections;
import nextstep.subway.station.application.dto.StationResponse;
import nextstep.subway.station.domain.Station;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PathFinderResult {
    private List<StationResponse> stations;
    private Sections sections;

    private PathFinderResult(List<StationResponse> stations, Sections sections) {
        this.stations = stations;
        this.sections = sections;
    }


    public static PathFinderResult of(List<Station> stations, List<Section> sections) {
        List<StationResponse> collect = stations.stream()
                .map(StationResponse::from)
                .collect(Collectors.toList());
        return new PathFinderResult(collect, new Sections(sections));
    }
}
