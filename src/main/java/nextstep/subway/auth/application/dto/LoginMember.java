package nextstep.subway.auth.application.dto;

import nextstep.subway.auth.application.User;

import java.util.Optional;

public class LoginMember implements User {
    private String email;

    public LoginMember(String email) {
        this.email = email;
    }


    @Override
    public Optional<String> getEmail() {
        return Optional.of(email);
    }
}
