package nextstep.subway.path.domain.policy;

import nextstep.subway.path.domain.FareCondition;

public interface FarePolicy {
    Long DEFAULT_DISTANCE = 10L;

    Long apply(FareCondition fareCondition, Long fare);
}
