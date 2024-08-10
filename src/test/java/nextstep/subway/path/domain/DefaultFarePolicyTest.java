package nextstep.subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultFarePolicyTest {

    @DisplayName("기본요금 계산")
    @ParameterizedTest
    @ValueSource(longs = {0L, 9L, 10L, 11L, 12L, 50L, 100L})
    void 기본요금_계산(Long distance) {
        //given
        DefaultFarePolicy defaultFarePolicy = new DefaultFarePolicy();
        // when
        Long fare = defaultFarePolicy.apply(distance);
        // then
        assertThat(fare).isEqualTo(DefaultFarePolicy.DEFAULT_FARE);
    }
}