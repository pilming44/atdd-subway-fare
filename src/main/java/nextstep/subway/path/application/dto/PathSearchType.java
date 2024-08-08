package nextstep.subway.path.application.dto;

public enum PathSearchType {
    DISTANCE,
    DURATION;

    public boolean isDuration() {
        return this.equals(DURATION);
    }
}
