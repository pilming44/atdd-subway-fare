package nextstep.subway.path.domain;

import nextstep.subway.line.domain.Newsection;
import nextstep.subway.line.domain.Section;
import nextstep.subway.path.application.dto.NewPathResponse;
import nextstep.subway.path.application.dto.PathResponse;
import nextstep.subway.path.application.dto.PathSearchType;
import nextstep.subway.station.domain.Station;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.List;

public class PathFinderBuilder {
    private PathFinder pathFinder;
    private WeightedMultigraph<Station, DefaultWeightedEdge> routeMap;
    private WeightedMultigraph<Station, CustomWeightedEdge> newrouteMap;
    private Station source;
    private Station target;

    public PathFinderBuilder(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
        this.routeMap = new WeightedMultigraph<>(DefaultWeightedEdge.class);
        this.newrouteMap = new WeightedMultigraph<>(CustomWeightedEdge.class);
    }

    public PathFinderBuilder addVertex(List<Station> stations) {
        stations.stream().forEach(this.routeMap::addVertex);
        return this;
    }

    public PathFinderBuilder addNewVertex(List<Station> stations) {
        stations.stream().forEach(this.newrouteMap::addVertex);
        return this;
    }

    public PathFinderBuilder addEdgeWeight(List<Section> sections) {
        sections.stream()
                .forEach(s -> this.routeMap.setEdgeWeight(
                        this.routeMap.addEdge(s.getUpStation(), s.getDownStation())
                        , s.getDistance()
                ));
        return this;
    }

    public PathFinderBuilder addNewEdgeWeight(List<Newsection> sections, PathSearchType type) {
        sections.stream()
                .forEach(s -> {
                    CustomWeightedEdge edge = new CustomWeightedEdge(type, s.getDistance(), s.getDuration());
                    this.newrouteMap.addEdge(s.getUpStation(), s.getDownStation(), edge);
                    this.newrouteMap.setEdgeWeight(edge, edge.getWeight());
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

    public NewPathResponse newfind() {
        return this.pathFinder.getNewPath(this.newrouteMap, this.source, this.target);
    }
}
