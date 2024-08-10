package nextstep.subway.path.domain;

public interface FarePolicy {
    Long DEFAULT_DISTANCE = 10L;

    void apply(FareCondition fareCondition);
}
