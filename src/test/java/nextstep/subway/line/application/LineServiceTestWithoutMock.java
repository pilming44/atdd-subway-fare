package nextstep.subway.line.application;

import nextstep.subway.line.application.dto.LineResponse;
import nextstep.subway.line.application.dto.SectionRequest;
import nextstep.subway.line.domain.Line;
import nextstep.subway.line.domain.LineRepository;
import nextstep.subway.line.domain.Section;
import nextstep.subway.line.domain.Sections;
import nextstep.subway.station.domain.Station;
import nextstep.subway.station.domain.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LineServiceTestWithoutMock {

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineService lineService;

    private Station 신사역;
    private Station 강남역;
    private Station 판교역;
    private Line 신분당선;

    @BeforeEach
    void setUp() {
        신사역 = stationRepository.save(new Station("신사역"));
        강남역 = stationRepository.save(new Station("강남역"));
        판교역 = stationRepository.save(new Station("판교역"));

        신분당선 = lineRepository.save(
                new Line("신분당선", "#77777", 0L
                        , LocalTime.parse("05:30", DateTimeFormatter.ofPattern("HH:mm"))
                        , LocalTime.parse("23:00", DateTimeFormatter.ofPattern("HH:mm"))
                        , Duration.ofMinutes(15)
        ));
    }

    @Test
    @DisplayName("노선 구간 추가")
    void addSection() {
        // Given
        SectionRequest 신사_강남_구간_추가_요청 = new SectionRequest(신사역.getId(), 강남역.getId(), 10L);

        // When
        LineResponse response = lineService.addSection(신분당선.getId(), 신사_강남_구간_추가_요청);

        // Then
        assertThat(response.getStations()).hasSize(2);
        assertThat(response.getStations().get(0).getId()).isEqualTo(신사역.getId());
        assertThat(response.getStations().get(1).getId()).isEqualTo(강남역.getId());
    }

    @Test
    @DisplayName("노선 구간 제거")
    void removeSection() {
        // Given
        SectionRequest 신사_강남_구간_추가_요청 = new SectionRequest(신사역.getId(), 강남역.getId(), 10L);
        SectionRequest 강남_판교_구간_추가_요청 = new SectionRequest(강남역.getId(), 판교역.getId(), 10L);
        lineService.addSection(신분당선.getId(), 신사_강남_구간_추가_요청);
        lineService.addSection(신분당선.getId(), 강남_판교_구간_추가_요청);

        // When
        lineService.removeSection(신분당선.getId(), 판교역.getId());

        // Then
        Line line = lineRepository.findById(신분당선.getId()).get();
        Sections sections = line.getSections();
        assertThat(sections.getSectionList()).hasSize(1);

        Section section = sections.getSectionList().get(0);
        assertThat(section.getUpStation()).isEqualTo(신사역);
        assertThat(section.getDownStation()).isEqualTo(강남역);
    }
}
