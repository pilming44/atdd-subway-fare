package nextstep.subway.path.domain;

import lombok.Getter;
import nextstep.subway.line.domain.Line;
import nextstep.subway.member.domain.AgeGroup;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FareCondition {
    private List<Line> lines = new ArrayList<>();
    private Long distance;
    private Long duration;
    private AgeGroup ageGroup;

    public FareCondition(List<Line> lines, Long distance, AgeGroup ageGroup) {
        this(lines, distance, 0L, ageGroup);
    }

    public FareCondition(List<Line> lines, Long distance, Long duration,AgeGroup ageGroup) {
        this.lines = lines;
        this.distance = distance;
        this.duration = duration;
        this.ageGroup = ageGroup;
    }
}
