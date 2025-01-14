package nextstep.subway.path.domain.strategy;

import nextstep.subway.line.domain.Section;

public interface PathWeightStrategy {
    double getWeight(Section section);
}
