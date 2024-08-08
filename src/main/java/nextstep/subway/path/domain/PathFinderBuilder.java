package nextstep.subway.path.domain;

import nextstep.subway.line.domain.Section;
import nextstep.subway.path.application.dto.PathResponse;
import nextstep.subway.path.application.dto.PathSearchType;
import nextstep.subway.station.domain.Station;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.List;

public class PathFinderBuilder {
    private PathFinder pathFinder;
    private WeightedMultigraph<Station, CustomWeightedEdge> routeMap;
    private Station source;
    private Station target;

    public PathFinderBuilder(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
        this.routeMap = new WeightedMultigraph<>(CustomWeightedEdge.class);
    }

    public PathFinderBuilder addVertex(List<Station> stations) {
        stations.forEach(this.routeMap::addVertex);
        return this;
    }

    public PathFinderBuilder addEdgeWeight(List<Section> sections, PathSearchType type) {
        sections.forEach(s -> {
                    CustomWeightedEdge edge = new CustomWeightedEdge(type, s.getDistance(), s.getDuration());
                    this.routeMap.addEdge(s.getUpStation(), s.getDownStation(), edge);
                    this.routeMap.setEdgeWeight(edge, edge.getWeight());
                });

        return this;
    }

    public PathFinderBuilder setSource(Station station) {
        this.source = station;
        return this;
    }

    public PathFinderBuilder setTarget(Station station) {
        this.target = station;
        return this;
    }

    public PathResponse find() {
        return this.pathFinder.getPath(this.routeMap, this.source, this.target);
    }
}
