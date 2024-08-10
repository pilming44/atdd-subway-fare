package nextstep.subway.path.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("연령대별 할인 정책 관련")
class AgeGroupFarePolicyTest extends FareContext {
    @DisplayName("청소년은 운임에서 350원을 공제한 금액의 20% 할인")
    @Test
    void 청소년_할인() {
        //given
        AgeGroupFarePolicy ageGroupFarePolicy = new AgeGroupFarePolicy();
        청소년_교대_천당_최단거리_조회_결과.addFare(1350L);

        // when
        ageGroupFarePolicy.apply(청소년_교대_천당_최단거리_조회_결과);

        // then
        Long 교대_천당_요금 = 청소년_교대_천당_최단거리_조회_결과.getFare();

        assertThat(교대_천당_요금).isEqualTo(800L);
    }

    @DisplayName("어린이는 운임에서 350원을 공제한 금액의 50% 할인")
    @Test
    void 어린이_할인() {
        //given
        AgeGroupFarePolicy ageGroupFarePolicy = new AgeGroupFarePolicy();
        어린이_교대_판교_최단거리_조회_결과.addFare(1350L);

        // when
        ageGroupFarePolicy.apply(어린이_교대_판교_최단거리_조회_결과);

        // then
        Long 교대_판교_요금 = 어린이_교대_판교_최단거리_조회_결과.getFare();

        assertThat(교대_판교_요금).isEqualTo(500L);
    }

    @DisplayName("성인은 정상요금 부과")
    @Test
    void 성인_요금() {
        //given
        AgeGroupFarePolicy ageGroupFarePolicy = new AgeGroupFarePolicy();
        성인_교대_양재_최단거리_조회_결과.addFare(1350L);

        // when
        ageGroupFarePolicy.apply(성인_교대_양재_최단거리_조회_결과);

        // then
        Long 교대_양재_요금 = 성인_교대_양재_최단거리_조회_결과.getFare();

        assertThat(교대_양재_요금).isEqualTo(1350L);
    }
}