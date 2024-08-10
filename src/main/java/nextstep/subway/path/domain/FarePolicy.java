package nextstep.subway.path.domain;

public interface FarePolicy {
    Long DEFAULT_DISTANCE = 10L;
    Long apply(Long distance);
}
