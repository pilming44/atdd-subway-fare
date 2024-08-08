package nextstep.subway.exception;

public class NoSuchMemberException extends RuntimeException {
    public NoSuchMemberException() {
        super("존재하지 않는 사용자입니다.");
    }
}
