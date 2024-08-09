package nextstep.subway.path.domain;

import lombok.Getter;

@Getter
public class DefaultFarePolicy implements FarePolicy{
    private final Long DEFAULT_FARE = 1250L;

    @Override
    public Long applyPolicy(Long distance) {
        return DEFAULT_FARE;
    }
}
