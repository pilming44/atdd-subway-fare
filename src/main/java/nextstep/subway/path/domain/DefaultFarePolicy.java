package nextstep.subway.path.domain;

public class DefaultFarePolicy implements FarePolicy {
    public static final Long DEFAULT_FARE = 1250L;

    @Override
    public void apply(FareCondition fareCondition) {
        fareCondition.addFare(DEFAULT_FARE);
    }
}
