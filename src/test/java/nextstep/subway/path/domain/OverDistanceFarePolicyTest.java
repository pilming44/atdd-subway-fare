package nextstep.subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("거리별 추가요금 정책 관련")
public class OverDistanceFarePolicyTest extends FareContext {

    @DisplayName("10km 초과 ~ 50km 이하 거리별 추가요금 계산")
    @Test
    void 거리별_추가요금_10km_초과_50km_이하() {
        //given
        OverDistanceFarePolicy overDistanceFarePolicy = new OverDistanceFarePolicy();

        // when
        overDistanceFarePolicy.apply(비회원_교대_양재_최소시간_조회_결과);

        overDistanceFarePolicy.apply(비회원_교대_판교_최소시간_조회_결과);

        // then
        Long 교대_양재_요금 = 비회원_교대_양재_최소시간_조회_결과.getFare();
        Long 교대_판교_요금 = 비회원_교대_판교_최소시간_조회_결과.getFare();
        assertThat(교대_양재_요금).isEqualTo(200L);
        assertThat(교대_판교_요금).isEqualTo(100L);
    }

    @DisplayName("50km 초과 거리별 추가요금 계산")
    @Test
    void 거리별_추가요금_50km_초과() {
        //given
        OverDistanceFarePolicy overDistanceFarePolicy = new OverDistanceFarePolicy();

        // when
        overDistanceFarePolicy.apply(비회원_교대_천당_최단거리_조회_결과);
        overDistanceFarePolicy.apply(비회원_교대_천당_최소시간_조회_결과);

        // then
        Long 교대_천당_최단거리_요금 = 비회원_교대_천당_최단거리_조회_결과.getFare();
        Long 교대_천당_최소시간_요금 = 비회원_교대_천당_최소시간_조회_결과.getFare();
        assertThat(교대_천당_최단거리_요금).isEqualTo(1400L);
        assertThat(교대_천당_최소시간_요금).isEqualTo(1500L);
    }
}
