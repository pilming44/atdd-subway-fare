package nextstep.subway.path.domain.strategy;

import nextstep.subway.line.domain.Section;

public class DistancePathStrategy implements PathWeightStrategy {
    @Override
    public double getWeight(Section section) {
        return section.getDistance();
    }
}
