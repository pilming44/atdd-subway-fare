package nextstep.subway.auth.application.dto;

import nextstep.subway.auth.application.User;

import java.util.Optional;

public class NonLoginMember implements User {

    @Override
    public Optional<String> getEmail() {
        return Optional.empty();
    }
}
