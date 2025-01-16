package nextstep.subway.path.domain;

import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.Section;
import nextstep.subway.line.domain.Sections;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PathSimulatorTest {
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
    @DisplayName("출발 시간과 섹션이 주어졌을때 현재 노선에서 현재 역에 가장 빨리 도착하는시간 찾기(바로출발)")
    void 중간역_출발시간_찾기_바로출발() {
        // given
        List<Section> sectionList = 삼호선.getSections().getSectionList();
        Section section = sectionList.get(1);//남부터미널역-양재역 구간
        LocalDateTime departTime = LocalDateTime.parse("2025-01-15 06:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        // when
        LocalDateTime fastesDepartTime = PathSimulator.getFastestDepart(departTime, section);

        // then
        assertThat(fastesDepartTime).isEqualTo(LocalDateTime.parse("2025-01-15 06:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    @Test
    @DisplayName("출발 시간과 섹션이 주어졌을때 현재 노선에서 현재 역에 가장 빨리 도착하는시간 찾기(기다렸다출발)")
    void 중간역_출발시간_찾기_기다렸다출발() {
        // given
        List<Section> sectionList = 삼호선.getSections().getSectionList();
        Section section = sectionList.get(1);//남부터미널역-양재역 구간
        LocalDateTime departTime = LocalDateTime.parse("2025-01-15 13:46", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        // when
        LocalDateTime fastesDepartTime = PathSimulator.getFastestDepart(departTime, section);

        // then
        assertThat(fastesDepartTime).isEqualTo(LocalDateTime.parse("2025-01-15 13:50", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    @Test
    @DisplayName("출발 시간과 섹션이 주어졌을때 현재 노선에서 현재 역에 가장 빨리 도착하는시간 찾기(막차놓쳐서 오늘 첫차)")
    void 중간역_출발시간_찾기_막차지남_오늘_첫차() {
        // given
        List<Section> sectionList = 삼호선.getSections().getSectionList();
        Section section = sectionList.get(1);//남부터미널역-양재역 구간
        LocalDateTime departTime = LocalDateTime.parse("2025-01-15 01:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        // when
        LocalDateTime fastesDepartTime = PathSimulator.getFastestDepart(departTime, section);

        // then
        assertThat(fastesDepartTime).isEqualTo(LocalDateTime.parse("2025-01-15 06:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    @Test
    @DisplayName("출발 시간과 섹션이 주어졌을때 현재 노선에서 현재 역에 가장 빨리 도착하는시간 찾기(막차놓쳐서 다음날 첫차)")
    void 중간역_출발시간_찾기_막차지남_다음날_첫차() {
        // given
        List<Section> sectionList = 삼호선.getSections().getSectionList();
        Section section = sectionList.get(1);//남부터미널역-양재역 구간
        LocalDateTime departTime = LocalDateTime.parse("2025-01-15 23:48", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        // when
        LocalDateTime fastesDepartTime = PathSimulator.getFastestDepart(departTime, section);

        // then
        assertThat(fastesDepartTime).isEqualTo(LocalDateTime.parse("2025-01-16 06:05", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    @Test
    @DisplayName("출발 시간과 섹션이 주어졌을때 현재 노선에서 현재 역에 가장 빨리 도착하는시간 찾기(상행종점 막차놓쳐서 다음날 첫차)")
    void 상행종점_출발시간_찾기_막차지남_다음날_첫차() {
        // given
        List<Section> sectionList = 삼호선.getSections().getSectionList();
        Section section = sectionList.get(0);//강남역-남부터미널 구간
        LocalDateTime departTime = LocalDateTime.parse("2025-01-15 23:48", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        // when
        LocalDateTime fastesDepartTime = PathSimulator.getFastestDepart(departTime, section);

        // then
        assertThat(fastesDepartTime).isEqualTo(LocalDateTime.parse("2025-01-16 06:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

}