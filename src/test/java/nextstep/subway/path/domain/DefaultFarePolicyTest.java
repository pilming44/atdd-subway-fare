package nextstep.subway.path.domain;

import nextstep.subway.member.domain.AgeGroup;
import nextstep.subway.path.domain.policy.DefaultFarePolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("기본요금 정책 관련")
class DefaultFarePolicyTest extends FareContext {

    @DisplayName("기본요금 계산")
    @Test
    void 기본요금_계산() {
        //given
        DefaultFarePolicy defaultFarePolicy = new DefaultFarePolicy();
        FareCondition 비회원_요금_조회_조건 = 요금_조회_조건(비회원_교대_양재_최단거리_조회_결과, AgeGroup.NON_AGED);
        FareCondition 성인_요금_조회_조건 = 요금_조회_조건(회원_교대_양재_최단거리_조회_결과, AgeGroup.ADULT);
        FareCondition 청소년_요금_조회_조건 = 요금_조회_조건(회원_교대_양재_최단거리_조회_결과, AgeGroup.TEENAGER);
        FareCondition 어린이_요금_조회_조건 = 요금_조회_조건(회원_교대_양재_최단거리_조회_결과, AgeGroup.CHILD);


        // when
        Long 비회원_기본요금 = defaultFarePolicy.apply(비회원_요금_조회_조건, 0L);
        Long 성인_기본요금 = defaultFarePolicy.apply(성인_요금_조회_조건, 0L);
        Long 청소년_기본요금 = defaultFarePolicy.apply(청소년_요금_조회_조건, 0L);
        Long 어린이_기본요금 = defaultFarePolicy.apply(어린이_요금_조회_조건, 0L);

        // then
        assertThat(비회원_기본요금).isEqualTo(DefaultFarePolicy.DEFAULT_FARE);
        assertThat(성인_기본요금).isEqualTo(DefaultFarePolicy.DEFAULT_FARE);
        assertThat(청소년_기본요금).isEqualTo(DefaultFarePolicy.DEFAULT_FARE);
        assertThat(어린이_기본요금).isEqualTo(DefaultFarePolicy.DEFAULT_FARE);
    }
}