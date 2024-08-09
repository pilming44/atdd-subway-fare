package nextstep.subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class OverDistanceFarePolicyTest {

    @DisplayName("거리별 추가요금 계산")
    @ParameterizedTest
    @CsvSource(value = {"10:0", "11:100", "24:300", "25:300", "26:400"
            , "50:800", "51:900", "58:900", "59:1000", "97:1400", "98:1400", "99:1500"}, delimiter = ':')
    void 거리별_추가요금_계산(Long distance, Long fare) {
        //given
        OverDistanceFarePolicy overDistanceFarePolicy = new OverDistanceFarePolicy();
        // when
        Long calFare = overDistanceFarePolicy.applyPolicy(distance);
        // then
        assertThat(calFare).isEqualTo(fare);
    }
}
