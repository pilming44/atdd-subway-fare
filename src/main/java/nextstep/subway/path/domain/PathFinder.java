package nextstep.subway.path.domain;

import nextstep.subway.station.domain.Station;
import org.jgrapht.graph.WeightedMultigraph;

public interface PathFinder {
    PathFinderResult getPath(WeightedMultigraph<Station, CustomWeightedEdge> routeMap
            , Station source
            , Station target);
}
