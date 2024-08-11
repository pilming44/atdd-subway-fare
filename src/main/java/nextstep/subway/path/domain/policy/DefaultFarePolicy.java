package nextstep.subway.path.domain.policy;

import nextstep.subway.path.domain.FareCondition;

public class DefaultFarePolicy implements FarePolicy {
    public static final Long DEFAULT_FARE = 1250L;

    @Override
    public Long apply(FareCondition fareCondition, Long fare) {
        return DEFAULT_FARE;
    }
}
