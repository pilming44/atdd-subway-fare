package nextstep.subway.path.domain;

import nextstep.subway.path.application.dto.PathResponse;
import nextstep.subway.station.domain.Station;
import org.jgrapht.graph.WeightedMultigraph;

public interface PathFinder {
    PathResponse getPath(WeightedMultigraph<Station, CustomWeightedEdge> routeMap
            , Station source
            , Station target);
}
