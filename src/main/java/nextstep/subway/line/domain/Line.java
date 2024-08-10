package nextstep.subway.line.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.subway.station.domain.Station;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String color;

    private Long addedFare;

    @Embedded
    private Sections sections = new Sections();

    public Line(Long id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public Line(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public List<Station> getStations() {
        return sections.getStations();
    }

    public boolean addableSection(Station upStation, Station downStation, Long distance, Long duration) {
        return sections.addableSection(new Section(this, upStation, downStation, distance, duration));
    }

    public void addSection(Station upStation, Station downStation, Long distance, Long duration) {
        this.sections.addSection(new Section(this, upStation, downStation, distance, duration));
    }

    public void removeSection(Station station) {
        sections.removeSectionByStation(station);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
