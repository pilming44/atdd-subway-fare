package nextstep.subway.path.domain;

import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.Sections;
import nextstep.subway.station.application.dto.StationResponse;
import nextstep.subway.station.domain.Station;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.KShortestPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.Multigraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JgraphTest {
    private Station 교대역;
    private Station 강남역;
    private Station 양재역;
    private Station 남부터미널역;
    private Line 이호선;
    private Line 신분당선;
    private Line 삼호선;

    @BeforeEach
    void setup() {
        교대역 = new Station("교대역");
        강남역 = new Station("강남역");
        양재역 = new Station("양재역");
        남부터미널역 = new Station("남부터미널역");

        이호선 = new Line("2호선", "bg-green-600", 0L
                , LocalTime.parse("05:00", DateTimeFormatter.ofPattern("HH:mm"))
                , LocalTime.parse("23:00", DateTimeFormatter.ofPattern("HH:mm"))
                , Duration.ofMinutes(1));
        이호선.addSection(교대역, 강남역, 10L, 3L);

        신분당선 = new Line("신분당선", "bg-blue-600", 0L
                , LocalTime.parse("05:30", DateTimeFormatter.ofPattern("HH:mm"))
                , LocalTime.parse("23:00", DateTimeFormatter.ofPattern("HH:mm"))
                , Duration.ofMinutes(15));
        신분당선.addSection(강남역, 양재역, 10L, 3L);

        삼호선 = new Line("3호선", "bg-red-600", 0L
                , LocalTime.parse("06:00", DateTimeFormatter.ofPattern("HH:mm"))
                , LocalTime.parse("23:00", DateTimeFormatter.ofPattern("HH:mm"))
                , Duration.ofMinutes(5));
        삼호선.addSection(교대역, 남부터미널역, 2L, 5L);
        삼호선.addSection(남부터미널역, 양재역, 3L, 5L);
    }

    @Test
    @DisplayName("두 역 최단거리 기준 경로조회 시 경로, 거리, 소요시간 리턴")
    void 최단거리_기준_조회() {
//        // given
//        PathFinderBuilder pathFinderBuilder = JgraphPathFinder.searchBuilder()
//                .addPath(이호선, PathSearchType.ARRIVAL_TIME)
//                .addPath(신분당선, PathSearchType.ARRIVAL_TIME)
//                .addPath(삼호선, PathSearchType.ARRIVAL_TIME);
//
//        // when
//        List<Sections> pathFinderResult = pathFinderBuilder
//                .setSource(교대역)
//                .setTarget(양재역)
//                .find();
//
//        // then
//        assertThat(pathFinderResult).hasSize(2);
    }

    @Test
    @DisplayName("Jgraph 가장 빠른 도착 경로 조회")
    public void getKShortestPaths() {
        String source = "v3";
        String target = "v1";

        Multigraph<String, DefaultWeightedEdge> graph = new Multigraph(DefaultWeightedEdge.class);
        graph.addVertex("v1");
        graph.addVertex("v2");
        graph.addVertex("v3");

        graph.addEdge("v1", "v2");
        graph.addEdge("v2", "v3");
        graph.addEdge("v1", "v3");

        List<GraphPath> paths = new KShortestPaths(graph, 1000).getPaths(source, target);

        assertThat(paths).hasSize(2);
        paths.forEach(it -> {
            assertThat(it.getVertexList()).startsWith(source);
            assertThat(it.getVertexList()).endsWith(target);
        });
    }
}
