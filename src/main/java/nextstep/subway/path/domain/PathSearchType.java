package nextstep.subway.path.domain;

public enum PathSearchType {
    DISTANCE,
    DURATION,
    ARRIVAL_TIME;

    public boolean isDuration() {
        return this == DURATION;
    }
}
