package nextstep.subway.line.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.subway.exception.IllegalSectionException;
import nextstep.subway.station.domain.Station;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Line line;

    @ManyToOne(fetch = FetchType.LAZY)
    private Station upStation;

    @ManyToOne(fetch = FetchType.LAZY)
    private Station downStation;

    private Long distance;

    public Section(Line line, Station upStation, Station downStation, Long distance) {
        validateStations(upStation, downStation);
        this.line = line;
        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    public boolean containsStation(Station station) {
        return upStation.equals(station) || downStation.equals(station);
    }

    private static void validateStations(Station upStation, Station downStation) {
        if (upStation.equals(downStation)) {
            throw new IllegalSectionException("구간의 상행역과 하행역이 같을수없습니다.");
        }
    }

    public void setLine(Line line) {
        this.line = line;
    }

}
