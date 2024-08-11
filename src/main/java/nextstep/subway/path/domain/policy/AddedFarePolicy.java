package nextstep.subway.path.domain.policy;

import nextstep.subway.line.domain.Line;
import nextstep.subway.path.domain.FareCondition;

import java.util.List;

public class AddedFarePolicy implements FarePolicy {
    @Override
    public Long apply(FareCondition fareCondition, Long fare) {
        List<Line> lines = fareCondition.getLines();

        long addedFare = lines.stream()
                .mapToLong(Line::getAddedFare)
                .max()
                .orElse(0L);

        return addedFare;
    }
}
