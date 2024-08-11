package nextstep.subway.path.domain;

import nextstep.subway.exception.IllegalPathException;
import nextstep.subway.line.domain.Section;
import nextstep.subway.station.domain.Station;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.List;
import java.util.stream.Collectors;

public class DijkstraShortestPathFinder implements PathFinder {
    public static PathFinderBuilder searchBuilder() {
        return new PathFinderBuilder(new DijkstraShortestPathFinder());
    }

    @Override
    public PathFinderResult getPath(WeightedMultigraph<Station, CustomWeightedEdge> routeMap
            , Station source
            , Station target) {
        validateEqualStation(source, target);
        validateStationExist(routeMap, source, target);

        DijkstraShortestPath<Station, CustomWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<>(routeMap);
        GraphPath<Station, CustomWeightedEdge> path = dijkstraShortestPath.getPath(source, target);

        validateLinkedPath(path);

        List<Station> shortestPathStation = path.getVertexList();

        List<Section> sections = path.getEdgeList().stream()
                .map(CustomWeightedEdge::getSection)
                .collect(Collectors.toList());

        return PathFinderResult.of(shortestPathStation, sections);
    }

    private void validateEqualStation(Station source, Station target) {
        if (source.equals(target)) {
            throw new IllegalPathException("출발역과 도착역이 같은 경우 경로를 조회할수 없습니다.");
        }
    }

    private void validateLinkedPath(GraphPath path) {
        if (path == null) {
            throw new IllegalPathException("출발역과 도착역이 연결되어있지 않습니다.");
        }
    }

    private void validateStationExist(WeightedMultigraph<Station, CustomWeightedEdge> routeMap
            , Station source
            , Station target) {
        if (!routeMap.containsVertex(source)) {
            throw new IllegalPathException("출발역이 경로에 존재하지 않습니다.");
        }
        if (!routeMap.containsVertex(target)) {
            throw new IllegalPathException("도착역이 경로에 존재하지 않습니다.");
        }
    }
}
