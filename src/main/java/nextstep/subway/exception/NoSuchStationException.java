package nextstep.subway.exception;

public class NoSuchStationException extends RuntimeException {
    public NoSuchStationException() {
        super("존재하지 않는 역입니다.");
    }
}
