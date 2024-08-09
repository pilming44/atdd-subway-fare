package nextstep.subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class OverDistanceFarePolicyTest {

    @DisplayName("거리별 추가요금 계산")
    @ParameterizedTest
    @CsvSource(value = {"10:0", "11:0", "24:200", "25:300", "26:300"
            , "50:800", "57:800", "58:900", "97:1300", "98:1400", "99:1400"}, delimiter = ':')
    void 거리별_추가요금_계산(Long distance, Long fare) {
        // when
        Long calFare = OverDistanceFarePolicy.applyPolicy(distance);
        // then
        assertThat(calFare).isEqualTo(fare);
    }
}
