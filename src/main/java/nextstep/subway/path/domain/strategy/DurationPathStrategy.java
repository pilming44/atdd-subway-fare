package nextstep.subway.path.domain.strategy;

import nextstep.subway.line.domain.Section;

public class DurationPathStrategy implements PathWeightStrategy {
    @Override
    public double getWeight(Section section) {
        return section.getDuration();  // duration 필드를 사용
    }
}
