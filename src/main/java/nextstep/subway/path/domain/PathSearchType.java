package nextstep.subway.path.domain;

public enum PathSearchType {
    DISTANCE,
    DURATION;

    public boolean isDuration() {
        return this == DURATION;
    }
}
