package nextstep.subway.path.domain;

import nextstep.subway.line.domain.Line;
import nextstep.subway.path.application.dto.PathSearchType;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

public class FareContext {
    protected Station 교대역;
    protected Station 강남역;
    protected Station 양재역;
    protected Station 남부터미널역;
    protected Station 서울역;
    protected Station 판교역;
    protected Station 천당역;


    protected Line 이호선;
    protected Line 신분당선;
    protected Line 삼호선;
    protected Line 가상선;
    protected Line 천당선;

    protected List<Line> allLines = new ArrayList<>();

    PathFinderResult 교대_양재_최단거리_조회_결과;
    PathFinderResult 교대_판교_최단거리_조회_결과;
    PathFinderResult 교대_천당_최단거리_조회_결과;
    PathFinderResult 교대_양재_최소시간_조회_결과;
    PathFinderResult 교대_판교_최소시간_조회_결과;
    PathFinderResult 교대_천당_최소시간_조회_결과;

    @BeforeEach
    void setup() {
        교대역 = new Station("교대역");
        강남역 = new Station("강남역");
        양재역 = new Station("양재역");
        남부터미널역 = new Station("남부터미널역");
        서울역 = new Station("서울역");
        판교역 = new Station("판교역");
        천당역 = new Station("천당역");

        이호선 = new Line("2호선", "green", 0L);
        신분당선 = new Line("신분당선", "red", 0L);
        삼호선 = new Line("삼호선", "orange", 0L);
        가상선 = new Line("가상선", "blue", 900L);
        천당선 = new Line("천당선", "white", 2000L);

        이호선.addSection(교대역, 강남역, 10L, 3L);

        신분당선.addSection(강남역, 양재역, 10L, 3L);
        신분당선.addSection(양재역, 판교역, 3L, 5L);

        삼호선.addSection(교대역, 남부터미널역, 2L, 5L);
        삼호선.addSection(남부터미널역, 양재역, 3L, 5L);

        가상선.addSection(남부터미널역, 서울역, 10L, 2L);
        가상선.addSection(서울역, 판교역, 3L, 3L);

        천당선.addSection(서울역, 천당역, 87L, 88L);

        allLines.add(이호선);
        allLines.add(신분당선);
        allLines.add(삼호선);
        allLines.add(가상선);
        allLines.add(천당선);

        교대_양재_최단거리_조회_결과 = 경로조회(교대역, 양재역, PathSearchType.DISTANCE);
        교대_판교_최단거리_조회_결과 = 경로조회(교대역, 판교역, PathSearchType.DISTANCE);
        교대_천당_최단거리_조회_결과 = 경로조회(교대역, 천당역, PathSearchType.DISTANCE);
        교대_양재_최소시간_조회_결과 = 경로조회(교대역, 양재역, PathSearchType.DURATION);
        교대_판교_최소시간_조회_결과 = 경로조회(교대역, 판교역, PathSearchType.DURATION);
        교대_천당_최소시간_조회_결과 = 경로조회(교대역, 천당역, PathSearchType.DURATION);

    }

    private PathFinderResult 경로조회(Station source, Station target, PathSearchType type) {
        return DijkstraShortestPathFinder.searchBuilder()
                .addAllPath(allLines, type)
                .setSource(source)
                .setTarget(target)
                .find();
    }
}
