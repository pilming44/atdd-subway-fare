package nextstep.subway.path.domain;

import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.Section;
import nextstep.subway.line.domain.Sections;
import nextstep.subway.path.domain.strategy.DistancePathStrategy;
import nextstep.subway.path.domain.strategy.DurationPathStrategy;
import nextstep.subway.path.domain.strategy.PathWeightStrategy;
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

    private PathWeightStrategy createStrategy(PathSearchType type) {
        if (type == PathSearchType.DISTANCE) {
            return new DistancePathStrategy();
        } else if (type == PathSearchType.DURATION) {
            return new DurationPathStrategy();
        }

        throw new UnsupportedOperationException("지원하지 않는 PathSearchType입니다.");
    }

    public PathFinderBuilder addPath(Line line, PathSearchType type) {
        PathWeightStrategy strategy = createStrategy(type);

        addVertex(line.getStations());

        Sections sections = line.getSections();

        addEdgeWeight(sections.getSectionList(), strategy);
        return this;
    }

    public PathFinderBuilder addAllPath(List<Line> lines, PathSearchType type) {
        PathWeightStrategy strategy = createStrategy(type);

        lines.forEach(line -> {
            addVertex(line.getStations());

            Sections sections = line.getSections();

            addEdgeWeight(sections.getSectionList(), strategy);
        });

        return this;
    }

    private void addVertex(List<Station> stations) {
        stations.forEach(this.routeMap::addVertex);
    }

    private void addEdgeWeight(List<Section> sections, PathWeightStrategy strategy) {
        sections.forEach(section -> {
            CustomWeightedEdge edge = new CustomWeightedEdge(section, strategy);
            this.routeMap.addEdge(section.getUpStation(), section.getDownStation(), edge);
            this.routeMap.setEdgeWeight(edge, edge.getWeight());
        });
    }

    public PathFinderBuilder setSource(Station station) {
        this.source = station;
        return this;
    }

    public PathFinderBuilder setTarget(Station station) {
        this.target = station;
        return this;
    }

    public PathFinderResult find() {
        return this.pathFinder.getPath(this.routeMap, this.source, this.target);
    }
}
