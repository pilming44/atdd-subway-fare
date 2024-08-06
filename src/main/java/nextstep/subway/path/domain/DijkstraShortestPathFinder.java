package nextstep.subway.path.domain;

import nextstep.subway.exception.IllegalPathException;
import nextstep.subway.line.domain.Newsection;
import nextstep.subway.line.domain.Section;
import nextstep.subway.path.application.dto.NewPathResponse;
import nextstep.subway.path.application.dto.PathResponse;
import nextstep.subway.station.domain.Station;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.List;

public class DijkstraShortestPathFinder implements PathFinder {
    public static PathFinderBuilder searchBuilder() {
        return new PathFinderBuilder(new DijkstraShortestPathFinder());
    }

    @Override
    public PathResponse getPath(WeightedMultigraph<Station, DefaultWeightedEdge> routeMap
            , Station source
            , Station target) {

        validateEqualStation(source, target);
        validateStationExist(routeMap, source, target);

        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath(routeMap);
        GraphPath path = dijkstraShortestPath.getPath(source, target);

        validateLinkedPath(path);

        List<Station> shortestPath = path.getVertexList();

        long shortestDistance = (long) dijkstraShortestPath.getPathWeight(source, target);

        return PathResponse.of(shortestPath, shortestDistance);
    }

    @Override
    public NewPathResponse getNewPath(WeightedMultigraph<Station, CustomWeightedEdge> routeMap
            , Station source
            , Station target) {
        validateEqualStation(source, target);
        newvalidateStationExist(routeMap, source, target);

        DijkstraShortestPath<Station, CustomWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<>(routeMap);
        GraphPath<Station, CustomWeightedEdge> path = dijkstraShortestPath.getPath(source, target);

        validateLinkedPath(path);

        List<Station> shortestPath = path.getVertexList();

        long distance = path.getEdgeList().stream().mapToLong(CustomWeightedEdge::getDistance).sum();
        long duration = path.getEdgeList().stream().mapToLong(CustomWeightedEdge::getDuration).sum();

        return NewPathResponse.of(shortestPath, distance, duration);
    }

    private static void validateEqualStation(Station source, Station target) {
        if (source.equals(target)) {
            throw new IllegalPathException("출발역과 도착역이 같은 경우 경로를 조회할수 없습니다.");
        }
    }

    private static void validateLinkedPath(GraphPath path) {
        if (path == null) {
            throw new IllegalPathException("출발역과 도착역이 연결되어있지 않습니다.");
        }
    }

    private static void validateStationExist(WeightedMultigraph<Station, DefaultWeightedEdge> routeMap
            , Station source
            , Station target) {
        if (!routeMap.containsVertex(source)) {
            throw new IllegalPathException("출발역이 경로에 존재하지 않습니다.");
        }
        if (!routeMap.containsVertex(target)) {
            throw new IllegalPathException("도착역이 경로에 존재하지 않습니다.");
        }
    }

    private static void newvalidateStationExist(WeightedMultigraph<Station, CustomWeightedEdge> routeMap
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
