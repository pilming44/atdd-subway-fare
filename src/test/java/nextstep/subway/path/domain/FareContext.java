package nextstep.subway.path.domain;

import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.Section;
import nextstep.subway.member.domain.AgeGroup;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FareContext {
    /*

     교대역    --- *2호선* ---   강남역
     |                        |
     *3호선*                   *신분당선*
     |                        |
     남부터미널역  --- *3호선* ---   양재
     |                        |
     *가상선*                   *신분당선*
     |                        |
     서울역  --- *가상선* ---   판교역
     |
     *천당선*
     |
     천당역

     노선 별 추가요금
     가상선 : 900원
     천당선 : 2000원
     */
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

    PathFinderResult 비회원_교대_양재_최단거리_조회_결과;
    PathFinderResult 비회원_교대_판교_최단거리_조회_결과;
    PathFinderResult 비회원_교대_천당_최단거리_조회_결과;
    PathFinderResult 비회원_교대_양재_최소시간_조회_결과;
    PathFinderResult 비회원_교대_판교_최소시간_조회_결과;
    PathFinderResult 비회원_교대_천당_최소시간_조회_결과;

    PathFinderResult 회원_교대_양재_최단거리_조회_결과;
    PathFinderResult 회원_교대_판교_최단거리_조회_결과;
    PathFinderResult 회원_교대_천당_최단거리_조회_결과;

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

        비회원_교대_양재_최단거리_조회_결과 = 비회원_경로조회(교대역, 양재역, PathSearchType.DISTANCE);
        비회원_교대_판교_최단거리_조회_결과 = 비회원_경로조회(교대역, 판교역, PathSearchType.DISTANCE);
        비회원_교대_천당_최단거리_조회_결과 = 비회원_경로조회(교대역, 천당역, PathSearchType.DISTANCE);
        비회원_교대_양재_최소시간_조회_결과 = 비회원_경로조회(교대역, 양재역, PathSearchType.DURATION);
        비회원_교대_판교_최소시간_조회_결과 = 비회원_경로조회(교대역, 판교역, PathSearchType.DURATION);
        비회원_교대_천당_최소시간_조회_결과 = 비회원_경로조회(교대역, 천당역, PathSearchType.DURATION);

        회원_교대_양재_최단거리_조회_결과 = 회원_경로조회(교대역, 양재역, PathSearchType.DISTANCE);
        회원_교대_판교_최단거리_조회_결과 = 회원_경로조회(교대역, 판교역, PathSearchType.DISTANCE);
        회원_교대_천당_최단거리_조회_결과 = 회원_경로조회(교대역, 천당역, PathSearchType.DISTANCE);
    }

    private PathFinderResult 비회원_경로조회(Station source, Station target, PathSearchType type) {
        return DijkstraShortestPathFinder.searchBuilder()
                .addAllPath(allLines, type)
                .setSource(source)
                .setTarget(target)
                .find();
    }

    private PathFinderResult 회원_경로조회(Station source, Station target, PathSearchType type) {
        return DijkstraShortestPathFinder.searchBuilder()
                .addAllPath(allLines, type)
                .setSource(source)
                .setTarget(target)
                .find();
    }

    protected FareCondition 요금_조회_조건(PathFinderResult pathFinderResult, AgeGroup ageGroup) {
        Long totalDistance = pathFinderResult.getSections().getTotalDistance();
        List<Line> lines = extractLineInfo(pathFinderResult);

        return new FareCondition(lines, totalDistance, ageGroup);
    }

    private List<Line> extractLineInfo(PathFinderResult pathFinderResult) {
        return pathFinderResult.getSections().getSectionList().stream()
                .map(Section::getLine)
                .distinct()
                .collect(Collectors.toList());
    }
}
