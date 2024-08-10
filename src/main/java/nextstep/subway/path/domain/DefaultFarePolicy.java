package nextstep.subway.path.domain;

public class DefaultFarePolicy implements FarePolicy {
    public static final Long DEFAULT_FARE = 1250L;

    @Override
    public Long apply(PathFinderResult pathFinderResult) {
        return DEFAULT_FARE;
    }
}
