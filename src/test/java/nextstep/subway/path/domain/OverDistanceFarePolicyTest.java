package nextstep.subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class OverDistanceFarePolicyTest extends FareContext {

    @DisplayName("10km 초과 ~ 50km 이하 거리별 추가요금 계산")
    @Test
    void 거리별_추가요금_10km_초과_50km_이하() {
        //given
        OverDistanceFarePolicy overDistanceFarePolicy = new OverDistanceFarePolicy();
        // when
        Long 교대_양재_요금 = overDistanceFarePolicy.apply(교대_양재_최소시간_조회_결과);
        Long 교대_판교_요금 = overDistanceFarePolicy.apply(교대_판교_최소시간_조회_결과);
        // then
        assertThat(교대_양재_요금).isEqualTo(200L);
        assertThat(교대_판교_요금).isEqualTo(100);
    }

    @DisplayName("50km 초과 거리별 추가요금 계산")
    @Test
    void 거리별_추가요금_50km_초과() {
        //given
        OverDistanceFarePolicy overDistanceFarePolicy = new OverDistanceFarePolicy();
        // when
        Long 교대_천당_최단거리_요금 = overDistanceFarePolicy.apply(교대_천당_최단거리_조회_결과);
        Long 교대_천당_최소시간_요금 = overDistanceFarePolicy.apply(교대_천당_최소시간_조회_결과);
        // then
        assertThat(교대_천당_최단거리_요금).isEqualTo(1400L);
        assertThat(교대_천당_최소시간_요금).isEqualTo(1500L);
    }
}
