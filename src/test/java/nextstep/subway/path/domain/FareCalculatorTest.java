package nextstep.subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class FareCalculatorTest {

    @DisplayName("거리별 요금 조회")
    @ParameterizedTest
    @CsvSource(value = {"0:1250","10:1250", "11:1350", "24:1550", "25:1550", "26:1650"
            , "50:2050", "51:2150", "58:2150", "59:2250", "97:2650", "98:2650", "99:2750"}, delimiter = ':')
    void 거리별_요금_조회(Long distance, Long fare) {
        FareCalculator fareCalculator = new FareCalculator();

        // when
        Long calFare = fareCalculator.getFare(distance);

        // then
        assertThat(calFare).isEqualTo(fare);
    }
}
