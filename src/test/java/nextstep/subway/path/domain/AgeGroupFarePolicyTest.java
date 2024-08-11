package nextstep.subway.path.domain;

import nextstep.subway.member.domain.AgeGroup;
import nextstep.subway.path.domain.policy.AgeGroupFarePolicy;
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

        FareCondition 청소년_교대_천당_요금_조회_조건 = 요금_조회_조건(회원_교대_천당_최단거리_조회_결과, AgeGroup.TEENAGER);

        // when
        Long 청소년_교대_천당_요금 = ageGroupFarePolicy.apply(청소년_교대_천당_요금_조회_조건, 1350L);

        // then
        assertThat(청소년_교대_천당_요금).isEqualTo(800L);
    }

    @DisplayName("어린이는 운임에서 350원을 공제한 금액의 50% 할인")
    @Test
    void 어린이_할인() {
        //given
        AgeGroupFarePolicy ageGroupFarePolicy = new AgeGroupFarePolicy();
        FareCondition 어린이_교대_천당_요금_조회_조건 = 요금_조회_조건(회원_교대_천당_최단거리_조회_결과, AgeGroup.CHILD);

        // when
        Long 어린이_교대_천당_요금 = ageGroupFarePolicy.apply(어린이_교대_천당_요금_조회_조건, 1350L);

        // then
        assertThat(어린이_교대_천당_요금).isEqualTo(500L);
    }

    @DisplayName("성인은 정상요금 부과")
    @Test
    void 성인_요금() {
        //given
        AgeGroupFarePolicy ageGroupFarePolicy = new AgeGroupFarePolicy();
        FareCondition 성인_교대_천당_요금_조회_조건 = 요금_조회_조건(회원_교대_천당_최단거리_조회_결과, AgeGroup.ADULT);

        // when
        Long 성인_교대_천당_요금 = ageGroupFarePolicy.apply(성인_교대_천당_요금_조회_조건, 1350L);

        // then
        assertThat(성인_교대_천당_요금).isEqualTo(1350L);
    }
}